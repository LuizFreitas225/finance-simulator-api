package com.luiz.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.luiz.MockUtils
import com.luiz.repository.LoanSimulationRepository
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
class LoanControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var loanSimulationRepository: LoanSimulationRepository

    @Test
    fun simulateLoanAndSave() {
        val loanSimulationDTO = MockUtils.createValidLoanSimulation()
        val jsonContent = objectMapper.writeValueAsString(loanSimulationDTO)
        val expectedSimulation = MockUtils.createSaveLoanSimulation()

        val result = mockMvc.perform(
                post("/v1/loan/simulate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
        )
                .andExpect(status().isOk)
                .andReturn()


        val response = JSONObject(result.response.contentAsString)
        assertEquals(expectedSimulation.installments, response.get("installments"))
        assertEquals(expectedSimulation.totalPayable, BigDecimal(response.getString("totalPayable")))
        assertEquals(expectedSimulation.totalInterestPayable, BigDecimal(response.getString("totalInterestPayable")))

        // Verify the data is saved in the H2 database
        val savedLoanSimulation = loanSimulationRepository.findAll()
        assertTrue(savedLoanSimulation.isNotEmpty())
        expectedSimulation.id= savedLoanSimulation.first().id
        assertTrue(expectedSimulation.equals(savedLoanSimulation.first()))
    }
}