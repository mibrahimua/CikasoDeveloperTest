package com.mibrahimuadev.cikasodevelopertest.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.mibrahimuadev.cikasodevelopertest.data.model.User

@Dao
interface UserDao {

    @Query("SELECT Id, Username FROM user WHERE Username = :userName AND Password = :userPassword")
    suspend fun validateUser(userName: String, userPassword:String): User?

}