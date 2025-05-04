package com.luiz.service

import com.luiz.config.Constant
import com.luiz.exception.ErrorMenssage
import com.luiz.exception.LoanSimulationException
import com.luiz.model.LoanSimulation
import com.luiz.repository.LoanSimulationRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.Period

@Service
class LoanService(
    private val loanSimulationRepository: LoanSimulationRepository,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun executeLoanSimulation(loanSimulation: LoanSimulation): LoanSimulation {
        logger.info(
            "LoanService.executeLoanSimulation - start - loanValue {} birthDate {}",
            loanSimulation.loanValue,
            loanSimulation.birthDate,
        )
        this.validateSimulation(loanSimulation)
        loanSimulation.annualInterestRate = this.getAnnualInterestRate(loanSimulation.birthDate!!)
        // Utilizei o arredondamento com RoundingMode.DOWN para aproximar o valor gerado ao que é exibido
        // no site do Banco Central do Brasil (BCB) em alguns casos específicos.
        loanSimulation.installmentValue =
            this
                .calculateInstallmentValue(loanSimulation)
                .setScale(Constant.DECIMAL_SCALE_DEFAULT, RoundingMode.DOWN)
        loanSimulation.totalPayable =
            this
                .calculateTotalPayable(loanSimulation)
                .setScale(Constant.DECIMAL_SCALE_DEFAULT, RoundingMode.HALF_UP)
        loanSimulation.totalInterestPayable =
            this
                .calculateTotalInterestPaid(loanSimulation)
                .setScale(Constant.DECIMAL_SCALE_DEFAULT, RoundingMode.HALF_UP)
        val loanSimulationSaved = loanSimulationRepository.save(loanSimulation)

        logger.info("LoanService.executeLoanSimulation - end - id {}", loanSimulationSaved.id)
        return loanSimulationSaved
    }

    private fun calculateTotalInterestPaid(loanSimulation: LoanSimulation): BigDecimal =
        loanSimulation.totalPayable!!.subtract(loanSimulation.loanValue!!)

    private fun calculateTotalPayable(loanSimulation: LoanSimulation): BigDecimal =
        loanSimulation.installmentValue!!.multiply(BigDecimal(loanSimulation.installments!!))

    private fun calculateInstallmentValue(loanSimulation: LoanSimulation): BigDecimal {
        val monthlyInterestRate = getMonthlyInterestRate(loanSimulation.annualInterestRate!!)
        return calculateFixedInstallment(loanSimulation.loanValue!!, monthlyInterestRate, loanSimulation.installments!!)
    }

    private fun getMonthlyInterestRate(annualInterestRate: BigDecimal): BigDecimal =
        annualInterestRate.divide(
            BigDecimal(Constant.MONTHS_IN_YEAR),
            Constant.DECIMAL_PRECISION_PERCENTAGE,
            RoundingMode.HALF_UP,
        )

    private fun calculateFixedInstallment(
        loanValue: BigDecimal,
        monthlyInterestRate: BigDecimal,
        numberOfInstallments: Int,
    ): BigDecimal {
        // onePlusMonthlyInterestRate = (1 + r)
        val onePlusMonthlyInterestRate = BigDecimal.ONE + monthlyInterestRate

        // Aplica a inversão da potência, já que o método 'pow' não permite expoentes negativos.
        // denominator = (1 + r)^-n ou 1/(1 + r)^n
        val denominator =
            BigDecimal.ONE.divide(
                onePlusMonthlyInterestRate.pow(numberOfInstallments),
                Constant.DECIMAL_PRECISION_PERCENTAGE,
                RoundingMode.HALF_UP,
            )

        // numerator = PV * r
        val numerator = loanValue.multiply(monthlyInterestRate)

        return numerator.divide(BigDecimal.ONE - denominator, Constant.DECIMAL_PRECISION_PERCENTAGE, RoundingMode.HALF_UP)
    }

    private fun getAnnualInterestRate(birthDate: LocalDate): BigDecimal {
        val age = Period.between(birthDate, LocalDate.now()).years

        return when {
            age <= 25 -> BigDecimal("0.05")
            age in 26..40 -> BigDecimal("0.03")
            age in 41..60 -> BigDecimal("0.02")
            else -> BigDecimal("0.04")
        }
    }

    private fun validateSimulation(loanSimulation: LoanSimulation) {
        val loanSimulationException = LoanSimulationException("")
        if (Period.between(loanSimulation.birthDate, LocalDate.now()).years < Constant.MIN_AGE) {
            loanSimulationException.listError.add(ErrorMenssage.MIN_AGE)
        }
        // Mais verificações  aqui

        if (loanSimulationException.listError.isNotEmpty()) {
            logger.error("LoanService.validateSimulation - error - {}", loanSimulationException.listError)
            throw loanSimulationException
        }
    }
}
