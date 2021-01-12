package com.mibrahimuadev.cikasodevelopertest.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barang")
data class BarangEntity(
    @PrimaryKey(autoGenerate = true)
    val Id: Int = 0,
    val Nama: String,
    val Merk: String,
    val Keterangan: String
)
