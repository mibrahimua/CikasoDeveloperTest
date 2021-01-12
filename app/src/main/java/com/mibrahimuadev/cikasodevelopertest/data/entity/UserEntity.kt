package com.mibrahimuadev.cikasodevelopertest.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int,
    val Username: String,
    val Password: String
)
