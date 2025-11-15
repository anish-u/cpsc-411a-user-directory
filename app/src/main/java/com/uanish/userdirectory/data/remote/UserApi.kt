package com.uanish.userdirectory.data.remote

import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
