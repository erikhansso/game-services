package com.dharmit.game_services.game_session.service

import com.dharmit.game_services.game_session.entity.TestEntity
import com.dharmit.game_services.game_session.repository.TestRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TestService(private val testRepository: TestRepository) {

    @PreAuthorize("hasAuthority('GAME_SESSION_WRITE_DATA')")
    fun saveTestData(data: String): TestEntity {
        val testEntity = TestEntity(
            testValue = data,
            updated = LocalDateTime.now()
        )
        return testRepository.save(testEntity)
    }

    @PreAuthorize("hasAuthority('GAME_SESSION_READ_DATA')")
    fun findAllTestData(): List<TestEntity> {
        return testRepository.findAll()
    }
} 