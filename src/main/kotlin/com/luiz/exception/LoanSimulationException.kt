package com.luiz.exception

class LoanSimulationException(
    message: String,
) : RuntimeException(message) {
    val listError: MutableList<String> = mutableListOf()
}
