package com.dharmit.game_services.gateway.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "permissions")
class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true, nullable = false)
    val name: String,
    
    @Column(nullable = false)
    val created: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updated: LocalDateTime = LocalDateTime.now()
) 