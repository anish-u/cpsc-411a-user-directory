package com.uanish.userdirectory.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Single source of truth: UI listens to this
    @Query("SELECT * FROM users")
    fun getUsersFlow(): Flow<List<UserEntity>>

    // Search by name or email (case-insensitive)
    @Query("""
        SELECT * FROM users
        WHERE name LIKE '%' || :query || '%' 
           OR email LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchUsersFlow(query: String): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()
}
