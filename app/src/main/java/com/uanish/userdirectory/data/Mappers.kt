package com.uanish.userdirectory.data

import com.uanish.userdirectory.data.local.UserEntity
import com.uanish.userdirectory.data.remote.UserDto
import com.uanish.userdirectory.domain.User

fun UserDto.toEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        username = username,
        email = email,
        phone = phone,
        website = website
    )

fun UserEntity.toDomain(): User =
    User(
        id = id,
        name = name,
        email = email,
        phone = phone
    )
