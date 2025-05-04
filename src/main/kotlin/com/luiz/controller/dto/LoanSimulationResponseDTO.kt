package com.luiz.controller.dto

import com.luiz.model.LoanSimulation
import java.math.BigDecimal

data class LoanSimulationResponseDTO(
    val id: Long,
    val installments: Int,
    val totalPayable: BigDecimal,
    val totalInterestPayable: BigDecimal,
) {
    companion object {
        fun from(loanSimulation: LoanSimulation): LoanSimulationResponseDTO =
            LoanSimulationResponseDTO(
                id = loanSimulation.id!!,
                installments = loanSimulation.installments!!,
                totalPayable = loanSimulation.totalPayable!!,
                totalInterestPayable = loanSimulation.totalInterestPayable!!,
            )
    }
}
