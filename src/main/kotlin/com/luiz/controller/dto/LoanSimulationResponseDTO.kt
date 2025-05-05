package com.luiz.controller.dto

import com.luiz.model.LoanSimulation
import java.math.BigDecimal

data class LoanSimulationResponseDTO(
    val installments: Int,
    val totalPayable: BigDecimal,
    val totalInterestPayable: BigDecimal,
) {
    companion object {
        fun from(loanSimulation: LoanSimulation): LoanSimulationResponseDTO =
            LoanSimulationResponseDTO(
                installments = loanSimulation.installments!!,
                totalPayable = loanSimulation.totalPayable!!,
                totalInterestPayable = loanSimulation.totalInterestPayable!!,
            )
    }
}
