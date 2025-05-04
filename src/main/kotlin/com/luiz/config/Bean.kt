package com.luiz.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class Bean {
    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()
}
