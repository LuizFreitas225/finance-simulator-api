package com.luiz.controller.dto

import com.luiz.exception.ErrorMenssage
import com.luiz.model.LoanSimulation
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Past
import org.jetbrains.annotations.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class LoanSimulationDTO(
    @field:NotNull(ErrorMenssage.LOAN_VALUE_NOT_NULL)
    @field:DecimalMin(value = "0.0", inclusive = false, message = ErrorMenssage.LOAN_VALUE_MIN)
    val loanValue: BigDecimal,
    @field:NotNull(ErrorMenssage.BIRTH_DATE_NOT_NULL)
    @field:Past(message = ErrorMenssage.BIRTH_DATE_PAST)
    val birthDate: LocalDate,
    @field:NotNull(ErrorMenssage.INSTALLMENTS_NOT_NULL)
    @field:Min(value = 1, message = ErrorMenssage.INSTALLMENTS_MIN)
    val installments: Int,
    @field:Email(message = ErrorMenssage.EMAIL_VALID)
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
