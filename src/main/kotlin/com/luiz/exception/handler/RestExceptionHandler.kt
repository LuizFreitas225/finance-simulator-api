package com.luiz.exception.handler

import com.luiz.exception.LoanSimulationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ExceptionResponseDefault> {
        val errors = exception.bindingResult.fieldErrors.map { it.defaultMessage }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionResponseDefault(
                tag = "input-validation",
                message = errors as List<String>,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
        )
    }

    @ExceptionHandler(LoanSimulationException::class)
    fun loanSimulationException(exception: LoanSimulationException): ResponseEntity<ExceptionResponseDefault> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ExceptionResponseDefault(
                tag = "data-validation",
                message = exception.listError,
                status = HttpStatus.BAD_REQUEST.value(),
            ),
        )
}
