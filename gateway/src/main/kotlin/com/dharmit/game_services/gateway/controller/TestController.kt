package com.dharmit.game_services.gateway.controller

import com.dharmit.game_services.gateway.service.TestService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/api/test")
class TestController(private val testService: TestService) {

    data class TestRequest(val data: String)
    data class TestResponse(val id: Long?, val message: String)
    data class TestListResponse(val id: Long?, val testValue: String?, val created: String, val updated: String)

    @PostMapping
    fun createTest(@RequestBody request: TestRequest): ResponseEntity<TestResponse> {
        val savedEntity = testService.saveTestData(request.data)
        return ResponseEntity.ok(
            TestResponse(
                id = savedEntity.id,
                message = "Data saved successfully"
            )
        )
    }
    
    @GetMapping
    fun getAllTests(): ResponseEntity<List<TestListResponse>> {
        val testEntities = testService.findAllTestData()
        val response = testEntities.map { entity ->
            TestListResponse(
                id = entity.id,
                testValue = entity.testValue,
                created = entity.created.toString(),
                updated = entity.updated.toString()
            )
        }
        return ResponseEntity.ok(response)
    }
} 