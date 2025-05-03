package com.luiz.controller.dto

import com.luiz.model.LoanSimulation
import java.math.BigDecimal

data class LoanSimulationResponseDTO(
    val id: Long,
    val installments: Int,
    val totalPayable: BigDecimal,
    val annualInterestRate: BigDecimal,
) {
    companion object {
        // TODO Add annualInterestRate to LoanSimulation and remover elvis operator when have values
        fun from(loanSimulation: LoanSimulation): LoanSimulationResponseDTO =
            LoanSimulationResponseDTO(
                id = loanSimulation.id ?: 0L,
                installments = loanSimulation.installments ?: 0,
                totalPayable = loanSimulation.totalPayable ?: BigDecimal.ZERO,
                annualInterestRate = BigDecimal("0.00"), // loanSimulation.annualInterestRate
            )
    }
}
