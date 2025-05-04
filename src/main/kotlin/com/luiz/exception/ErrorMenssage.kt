package com.luiz.exception

object ErrorMenssage {
    const val LOAN_VALUE_NOT_NULL = "Loan value cannot be null"
    const val LOAN_VALUE_MIN = "Loan value must be greater than 0"
    const val BIRTH_DATE_NOT_NULL = "Birth date cannot be null"
    const val BIRTH_DATE_PAST = "Birth date must be in the past"
    const val INSTALLMENTS_NOT_NULL = "Term in months cannot be null"
    const val INSTALLMENTS_MIN = "Term in months must be at least 1"
    const val EMAIL_VALID = "Email should be valid"
    const val MIN_AGE = "Minimum age is 18 years"
}
