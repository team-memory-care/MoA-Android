package com.moa.app.domain.auth.model

/**
 * 유저 역할 Role
 *
 * PARENT : 시니어 (치매환자)
 * CHILD : 보호자 (가족/돌보미)
 */
enum class UserRole {
    PARENT,
    CHILD,
    PENDING,
    ADMIN,
    ;

    companion object {
        fun fromString(userRole: String): UserRole {
            return when (userRole) {
                "PARENT" -> PARENT
                "CHILD" -> CHILD
                "PENDING" -> PENDING
                "ADMIN" -> ADMIN
                else -> throw IllegalArgumentException("Invalid UserRole: $userRole")
            }
        }
    }
}
