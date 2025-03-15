package com.dharmit.game_services.game_session.repository

import com.dharmit.game_services.game_session.entity.TestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository : JpaRepository<TestEntity, Long> 