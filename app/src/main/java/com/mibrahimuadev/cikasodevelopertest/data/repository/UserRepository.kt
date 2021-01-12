package com.mibrahimuadev.cikasodevelopertest.data.repository

import android.app.Application
import com.mibrahimuadev.cikasodevelopertest.data.AppDatabase
import com.mibrahimuadev.cikasodevelopertest.data.dao.UserDao
import com.mibrahimuadev.cikasodevelopertest.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(application: Application) {
    private val userDao: UserDao

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        userDao = database.userDao()
    }

    suspend fun validateUser(userName: String, userPassword:String): User? {
        return withContext(Dispatchers.IO) {
            return@withContext userDao.validateUser(userName, userPassword)
        }
    }
}