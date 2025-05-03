package com.luiz.controller.dto

import com.luiz.model.LoanSimulation
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Past
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class LoanSimulationDTO(
    @field:NotNull("Loan value cannot be null")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Loan value must be greater than 0")
    val loanValue: BigDecimal,
    @field:NotNull("Birth date cannot be null")
    @field:Past(message = "Birth date must be in the past")
    val birthDate: LocalDate,
    @field:NotNull("Term in months cannot be null")
    @field:Min(value = 1, message = "Term in months must be at least 1")
    val installments: Int,
    @field:Email(message = "Email should be valid")
    val email: String? = null,
) {
    fun toEntity(): LoanSimulation =
        LoanSimulation(
            loanValue = this.loanValue,
            birthDate = this.birthDate,
            installments = this.installments,
            email = this.email,
        )

    // Remove email from toString, because it will be used in log
    override fun toString(): String = "loanValue=$loanValue, birthDate=$birthDate, installments=$installments"
}
