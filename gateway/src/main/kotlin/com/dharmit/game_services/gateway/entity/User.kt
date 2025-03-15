package com.dharmit.game_services.gateway.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(unique = true, nullable = false)
    private val username: String,
    
    @Column(nullable = false)
    private var password: String,
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    val roles: MutableSet<Role> = mutableSetOf(),
    
    @Column(nullable = false)
    val created: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var updated: LocalDateTime = LocalDateTime.now(),
    
    @Column(nullable = false)
    var enabled: Boolean = true,
    
    @Column(nullable = false)
    var accountNonExpired: Boolean = true,
    
    @Column(nullable = false)
    var accountNonLocked: Boolean = true,
    
    @Column(nullable = false)
    var credentialsNonExpired: Boolean = true
) : UserDetails {
    
    override fun getUsername(): String = username
    
    override fun getPassword(): String = password
    
    fun setPassword(password: String) {
        this.password = password
        this.updated = LocalDateTime.now()
    }
    
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles.flatMap { role ->
            role.permissions.map { permission ->
                SimpleGrantedAuthority(permission.name)
            }
        }
    }
    
    override fun isEnabled(): Boolean = enabled
    
    override fun isAccountNonExpired(): Boolean = accountNonExpired
    
    override fun isAccountNonLocked(): Boolean = accountNonLocked
    
    override fun isCredentialsNonExpired(): Boolean = credentialsNonExpired
} 