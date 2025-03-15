package com.dharmit.game_services.matchmaking.service

import com.dharmit.game_services.matchmaking.entity.TestEntity
import com.dharmit.game_services.matchmaking.repository.TestRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TestService(private val testRepository: TestRepository) {

    fun saveTestData(data: String): TestEntity {
        val testEntity = TestEntity(
            testValue = data,
            updated = LocalDateTime.now()
        )
        return testRepository.save(testEntity)
    }

    fun findAllTestData(): List<TestEntity> {
        return testRepository.findAll()
    }
} 