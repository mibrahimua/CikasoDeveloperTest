package com.mibrahimuadev.cikasodevelopertest.data.repository

import android.app.Application
import com.mibrahimuadev.cikasodevelopertest.data.AppDatabase
import com.mibrahimuadev.cikasodevelopertest.data.dao.BarangDao
import com.mibrahimuadev.cikasodevelopertest.data.entity.BarangEntity
import com.mibrahimuadev.cikasodevelopertest.data.model.Barang
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BarangRepository(application: Application) {
    private val barangDao: BarangDao

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        barangDao = database.barangDao()
    }

    suspend fun getAllBarang(): List<Barang> {
        return withContext(Dispatchers.IO) {
            barangDao.getAllBarang()
        }
    }

    suspend fun getDetailBarang(idBarang: Int): Barang {
        return withContext((Dispatchers.IO)) {
            barangDao.getDetailBarang(idBarang)
        }
    }

    suspend fun insertOrUpdateBarang(barang: BarangEntity) {
        return withContext(Dispatchers.IO) {
            barangDao.insertOrUpdateBarang(barang)
        }
    }

    suspend fun deleteBarang(idBarang: Int) {
        return withContext(Dispatchers.IO) {
            barangDao.deleteBarang(idBarang)
        }
    }
}