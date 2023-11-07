package com.example.jeky.core.domain.model

import com.example.jeky.core.data.source.local.room.entity.UserEntity

data class User(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val phoneNumber: String = ""
)

fun User.toEntity() = UserEntity (
    this.email,
    this.password,
    this.name,
    this.phoneNumber
)