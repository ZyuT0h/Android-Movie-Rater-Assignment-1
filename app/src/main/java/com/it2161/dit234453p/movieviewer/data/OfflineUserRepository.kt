package com.it2161.dit234453p.movieviewer.data

import kotlinx.coroutines.flow.Flow

class OfflineUserRepository(private val userDao: UserDAO) :UserRepository {
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserStream(id: Int): Flow<User?> = userDao.getUserById(id)

    override suspend fun insertUser(user: User) = userDao.insertUser(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByName(username)
    }

    override suspend fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return userDao.getUserByUsernameAndPassword(username, password)
    }
}