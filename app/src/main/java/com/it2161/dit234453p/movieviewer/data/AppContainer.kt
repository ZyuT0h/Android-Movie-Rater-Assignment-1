package com.it2161.dit234453p.movieviewer.data

import android.content.Context

interface AppContainer {
    val userRepository: UserRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [UserRepository]
     */
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(UserDatabase.getDatabase(context).userDAO())
    }
}