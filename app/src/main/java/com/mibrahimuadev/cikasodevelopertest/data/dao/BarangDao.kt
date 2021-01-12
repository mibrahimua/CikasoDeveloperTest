package com.mibrahimuadev.cikasodevelopertest.data.dao

import androidx.room.*
import com.mibrahimuadev.cikasodevelopertest.data.entity.BarangEntity
import com.mibrahimuadev.cikasodevelopertest.data.model.Barang

@Dao
interface BarangDao {

    @Query("SELECT Id, Nama, Merk, Keterangan FROM barang")
    suspend fun getAllBarang(): List<Barang>

    @Query("SELECT Id, Nama, Merk, Keterangan FROM barang WHERE Id = :idBarang")
    suspend fun getDetailBarang(idBarang: Int): Barang

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBarang(barang: BarangEntity): Long

    @Update
    suspend fun updateBarang(barang: BarangEntity)

    @Transaction
    suspend fun insertOrUpdateBarang(barang: BarangEntity) {
        val id = insertBarang(barang)
        if (id == -1L) updateBarang(barang)
    }

    @Query("DELETE FROM barang WHERE Id = :idBarang")
    suspend fun deleteBarang(idBarang: Int)

}