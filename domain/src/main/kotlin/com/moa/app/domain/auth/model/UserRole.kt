package com.moa.app.domain.auth.model

/**
 * 유저 역할 Role
 *
 * PARENT : 본인
 * CHILD : 보호자
 */
enum class UserRole {
    PARENT,
    CHILD,
    ;

    companion object {
        fun fromString(userRole: String): UserRole {
            return when (userRole) {
                "PARENT" -> PARENT
                "CHILD" -> CHILD
                else -> throw IllegalArgumentException("Invalid UserRole: $userRole")
            }
        }
    }
}
