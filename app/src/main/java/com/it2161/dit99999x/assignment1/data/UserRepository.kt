package com.it2161.dit99999x.assignment1.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllUsersStream(): Flow<List<User>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getUserStream(id: Int): Flow<User?>

    /**
     * Insert item in the data source
     */
    suspend fun insertUser(user: User)

    /**
     * Delete item from the data source
     */
    suspend fun deleteUser(user: User)

    /**
     * Update item in the data source
     */
    suspend fun updateUser(user: User)

    suspend fun getUserByUsername(username: String): User?

    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

}