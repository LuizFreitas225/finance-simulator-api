package com.luiz

import com.luiz.model.LoanSimulation
import java.math.BigDecimal
import java.time.LocalDate

object MockUtils {
    fun createValidLoanSimulation(): LoanSimulation =
        LoanSimulation(
            loanValue = BigDecimal("10000.00"),
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
}
