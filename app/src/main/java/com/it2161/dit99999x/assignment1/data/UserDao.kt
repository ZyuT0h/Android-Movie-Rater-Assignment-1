package com.it2161.dit99999x.assignment1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE id = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("SELECT * from users ORDER BY userName ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE userName = :userName LIMIT 1")
    suspend fun getUserByName(userName: String): User?

    @Query("SELECT * FROM users WHERE userName = :userName AND password = :password LIMIT 1")
    suspend fun getUserByUsernameAndPassword(userName: String, password: String): User?

}