package com.dharmit.game_services.game_session.service

import com.dharmit.game_services.game_session.entity.TestEntity
import com.dharmit.game_services.game_session.repository.TestRepository
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