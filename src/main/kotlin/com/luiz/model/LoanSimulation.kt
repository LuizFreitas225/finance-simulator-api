package com.luiz.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate

@Table(name = "loan_simulation")
@Entity
class LoanSimulation() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var loanValue: BigDecimal? = null

    @Column(nullable = false)
    var birthDate: LocalDate? = null

    @Column(nullable = false)
    var installments: Int? = null

    @Column
    var email: String? = null

    @Column(nullable = false)
    var installmentValue: BigDecimal? = null

    @Column(nullable = false)
    var totalPayable: BigDecimal? = null

    @Column(nullable = false)
    var annualInterestRate: BigDecimal? = null

    @Column(nullable = false)
    var totalInterestPayable: BigDecimal? = null

    constructor(
        loanValue: BigDecimal,
        birthDate: LocalDate,
        installments: Int,
        email: String?,
    ) : this() {
        this.loanValue = loanValue
        this.birthDate = birthDate
        this.installments = installments
        this.email = email
    }
}
