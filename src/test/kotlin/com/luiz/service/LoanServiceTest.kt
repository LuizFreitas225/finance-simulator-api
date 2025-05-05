package com.luiz.service

import com.luiz.MockUtils
import com.luiz.exception.ErrorMenssage
import com.luiz.exception.LoanSimulationException
import com.luiz.model.LoanSimulation
import com.luiz.repository.LoanSimulationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDate
import java.time.ZoneId
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class LoanServiceTest {
    private val loanSimulationRepository: LoanSimulationRepository = mock()

    // Usando o Clock para viablizar os testes, possibilitando que a data atual não influêncie no teste atual
    private val clock: Clock =
        Clock.fixed(
            LocalDate.of(2025, 5, 2).atStartOfDay(ZoneId.systemDefault()).toInstant(),
            ZoneId.systemDefault(),
        )
    private val loanService = LoanService(loanSimulationRepository, clock)

    @Test
    fun loanSimulation_doSuccessfully() {
        val expectedLoanSimulation = MockUtils.createSaveLoanSimulation()

        `when`(loanSimulationRepository.save(any())).thenReturn(expectedLoanSimulation)

        val result = loanService.executeLoanSimulation(MockUtils.createValidLoanSimulation())

        assertEquals(expectedLoanSimulation, result)
        verify(loanSimulationRepository, times(1)).save(ArgumentMatchers.eq(expectedLoanSimulation))
    }

    @Test
    fun loanSimulation_doAgeMinimum() {
        val inputLoanSimulation = MockUtils.createValidLoanSimulation()
        `when`(loanSimulationRepository.save(any())).thenReturn(inputLoanSimulation)

        inputLoanSimulation.birthDate = LocalDate.now().minusYears(17)

        val exception =
            assertThrows<>(LoanSimulationException::class.java) {
                loanService.executeLoanSimulation(inputLoanSimulation)
            }

        assertNotNull(exception.listError)
        assertTrue(exception.listError.contains(ErrorMenssage.MIN_AGE))
    }

    @Test
    fun loanSimulation_doCorrectInterestRates() {
        val captor = ArgumentCaptor.forClass(LoanSimulation::class.java)
        `when`(loanSimulationRepository.save(captor.capture())).thenReturn(MockUtils.createValidLoanSimulation())

        // 18 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(2007, 1, 1))
        // 25 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(2000, 1, 1))
        // 26 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(1999, 1, 1))
        // 40 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(1985, 1, 1))
        // 41 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(1984, 1, 1))
        // 60 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(1965, 1, 1))
        // 65 anos
        this.loanSimulationWithCurrentDate(LocalDate.of(1960, 1, 1))

        val allSimulation = captor.allValues
        // 18 ao 25 anos
        assertEquals(BigDecimal.valueOf(0.05), allSimulation.get(0).annualInterestRate)
        assertEquals(BigDecimal.valueOf(0.05), allSimulation.get(1).annualInterestRate)

        // 26 ao 24 anos
        assertEquals(BigDecimal.valueOf(0.03), allSimulation.get(2).annualInterestRate)
        assertEquals(BigDecimal.valueOf(0.03), allSimulation.get(3).annualInterestRate)

        // 41 ao 60 anos
        assertEquals(BigDecimal.valueOf(0.02), allSimulation.get(4).annualInterestRate)
        assertEquals(BigDecimal.valueOf(0.02), allSimulation.get(5).annualInterestRate)

        // +60 anos
        assertEquals(BigDecimal.valueOf(0.04), allSimulation.get(6).annualInterestRate)
    }

    private fun loanSimulationWithCurrentDate(currentDate: LocalDate) {
        var inputLoanSimulation1 = MockUtils.createValidLoanSimulation()
        inputLoanSimulation1.birthDate = currentDate
        loanService.executeLoanSimulation(inputLoanSimulation1)
    }
}
