package com.luiz.service

import com.luiz.model.LoanSimulation
import com.luiz.repository.LoanSimulationRepository
import org.springframework.stereotype.Service

@Service
class LoanService(
    private val loanSimulationRepository: LoanSimulationRepository,
) {
    fun saveLoanSimulation(loanSimulation: LoanSimulation): LoanSimulation = loanSimulationRepository.save(loanSimulation)
}
