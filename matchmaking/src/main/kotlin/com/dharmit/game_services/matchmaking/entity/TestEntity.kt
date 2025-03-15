package com.dharmit.game_services.matchmaking.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "test")
data class TestEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, updatable = false)
    val created: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var updated: LocalDateTime = LocalDateTime.now(),

    @Column(name = "test_value")
    var testValue: String? = null
) 