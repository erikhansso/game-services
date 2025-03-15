package com.dharmit.game_services.matchmaking.repository

import com.dharmit.game_services.matchmaking.entity.TestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository : JpaRepository<TestEntity, Long> 