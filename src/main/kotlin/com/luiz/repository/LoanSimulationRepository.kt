package com.luiz.repository

import com.luiz.model.LoanSimulation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LoanSimulationRepository : JpaRepository<LoanSimulation, Long>
