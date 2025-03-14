package com.dharmit.game_services.gateway.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HelloWorldController {

    @GetMapping("/hello")
    fun sayHello(): Map<String, String> {
        return mapOf("message" to "Hello, World!")
    }
} 