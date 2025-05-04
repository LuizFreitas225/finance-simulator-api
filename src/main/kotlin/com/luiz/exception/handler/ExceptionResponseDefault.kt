package com.luiz.exception.handler

import java.time.LocalDateTime

class ExceptionResponseDefault(
    val tag: String,
    val message: List<String>,
    val status: Int,
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
