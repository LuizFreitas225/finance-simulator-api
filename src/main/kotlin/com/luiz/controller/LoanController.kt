package com.luiz.controller

import com.luiz.controller.dto.LoanSimulationDTO
import com.luiz.controller.dto.LoanSimulationResponseDTO
import com.luiz.service.LoanService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/loan")
class LoanController(
    private val service: LoanService,
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping("/simulate")
    fun simulate(
        @RequestBody @Valid loanSimulationDTO: LoanSimulationDTO,
    ): ResponseEntity<LoanSimulationResponseDTO> {
        logger.info("LoanController.simulate - start - input [{}]", loanSimulationDTO.toString())
        val simulation = service.executeLoanSimulation(loanSimulationDTO.toEntity())
        logger.info("LoanController.simulate - end - totalPayable = {}", simulation.totalPayable)
        return ResponseEntity.ok(LoanSimulationResponseDTO.from(simulation))
    }
}
