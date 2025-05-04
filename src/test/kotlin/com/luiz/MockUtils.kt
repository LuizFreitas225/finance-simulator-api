package com.luiz

import com.luiz.model.LoanSimulation
import java.math.BigDecimal
import java.time.LocalDate

object MockUtils {
    fun createValidLoanSimulation(): LoanSimulation =
        LoanSimulation(
            loanValue = BigDecimal("10000"),
            birthDate = LocalDate.of(1990, 1, 1),
            installments = 12,
            email = "test@example.com",
        )

    fun createSaveLoanSimulation(): LoanSimulation {
        val loanSimulation = createValidLoanSimulation()
        loanSimulation.annualInterestRate = BigDecimal("0.03")
        loanSimulation.installmentValue = BigDecimal("846.93")
        loanSimulation.totalPayable = BigDecimal("10163.16")
        loanSimulation.totalInterestPayable = BigDecimal("163.16")

        return loanSimulation
    }

//    fun createLoanSimulationWithInvalidAge(): LoanSimulation {
//        return LoanSimulation(
//                loanValue = BigDecimal("10000"),
//                birthDate = LocalDate.now().minusYears(17), // Below minimum age
//                installments = 12,
//                email = "test@example.com"
//        )
//    }

//    fun createLoanSimulationWithNullLoanValue(): LoanSimulation {
//        return LoanSimulation(
//                loanValue = null,
//                birthDate = LocalDate.of(1990, 1, 1),
//                installments = 12,
//                email = "test@example.com"
//        )
//    }
//
//    fun createLoanSimulationWithInvalidInstallments(): LoanSimulation {
//        return LoanSimulation(
//                loanValue = BigDecimal("10000"),
//                birthDate = LocalDate.of(1990, 1, 1),
//                installments = 0, // Invalid installments
//                email = "test@example.com"
//        )
//    }
}
