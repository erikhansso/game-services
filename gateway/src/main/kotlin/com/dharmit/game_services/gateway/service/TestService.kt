package com.dharmit.game_services.gateway.service

import com.dharmit.game_services.gateway.entity.TestEntity
import com.dharmit.game_services.gateway.repository.TestRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TestService(private val testRepository: TestRepository) {

    @PreAuthorize("hasAuthority('GATEWAY_WRITE_DATA')")
    fun saveTestData(data: String): TestEntity {
        val testEntity = TestEntity(
            testValue = data,
            updated = LocalDateTime.now()
        )
        return testRepository.save(testEntity)
    }

    @PreAuthorize("hasAuthority('GATEWAY_READ_DATA')")
    fun findAllTestData(): List<TestEntity> {
        return testRepository.findAll()
    }
} 