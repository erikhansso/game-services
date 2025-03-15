package com.dharmit.game_services.gateway.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true, nullable = false)
    val name: String,
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "role_permissions",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    val permissions: MutableSet<Permission> = mutableSetOf(),
    
    @Column(nullable = false)
    val created: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    val updated: LocalDateTime = LocalDateTime.now()
) 