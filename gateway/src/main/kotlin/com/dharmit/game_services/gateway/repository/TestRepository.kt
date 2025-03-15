package com.dharmit.game_services.gateway.repository

import com.dharmit.game_services.gateway.entity.TestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository : JpaRepository<TestEntity, Long> 