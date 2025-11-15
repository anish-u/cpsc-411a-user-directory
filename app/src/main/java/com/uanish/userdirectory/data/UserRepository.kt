package com.uanish.userdirectory.data

import com.uanish.userdirectory.data.local.UserDao
import com.uanish.userdirectory.data.local.UserEntity
import com.uanish.userdirectory.data.remote.UserApi
import com.uanish.userdirectory.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val api: UserApi,
    private val dao: UserDao
) {

    // Single source of truth: always from DB
    fun getUsers(query: String?): Flow<List<User>> {
        val flow: Flow<List<UserEntity>> =
            if (query.isNullOrBlank()) {
                dao.getUsersFlow()
            } else {
                dao.searchUsersFlow(query.trim())
            }

        return flow.map { list -> list.map { it.toDomain() } }
    }

    // Called on app open or pull-to-refresh
    suspend fun refreshUsers(): Result<Unit> {
        return try {
            val remoteUsers = api.getUsers()
            val entities = remoteUsers.map { it.toEntity() }

            // You can clear then insert, or just insert with REPLACE
            dao.insertUsers(entities)

            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
