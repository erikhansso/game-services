package com.dharmit.game_services.gateway.repository

import com.dharmit.game_services.gateway.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface RoleRepository : JpaRepository<Role, Long> {
    fun findByName(name: String): Optional<Role>
}
