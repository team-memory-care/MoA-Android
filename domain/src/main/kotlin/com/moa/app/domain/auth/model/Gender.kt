package com.moa.app.domain.auth.model

enum class Gender {
    MALE,
    FEMALE,

    ;

    companion object {
        fun fromString(gender: String): Gender {
            return when (gender) {
                "MALE" -> MALE
                "FEMALE" -> FEMALE
                else -> throw IllegalArgumentException("Invalid Gender: $gender")
            }
        }
    }
}
