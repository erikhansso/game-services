package com.dharmit.game_services.gateway.repository

import com.dharmit.game_services.gateway.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PermissionRepository : JpaRepository<Permission, Long> {
    fun findByName(name: String): Optional<Permission>
}
