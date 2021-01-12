package com.mibrahimuadev.cikasodevelopertest.ui.barang

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mibrahimuadev.cikasodevelopertest.data.entity.BarangEntity
import com.mibrahimuadev.cikasodevelopertest.data.model.Barang
import com.mibrahimuadev.cikasodevelopertest.data.repository.BarangRepository
import com.mibrahimuadev.cikasodevelopertest.utils.Event
import kotlinx.coroutines.launch

class StokViewModel(application: Application) : AndroidViewModel(application) {
    private val barangRepository: BarangRepository

    init {
        Log.i("StokViewModel", "StokViewModel created")
        barangRepository = BarangRepository(application)
    }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _allBarangs = MutableLiveData<List<Barang>>()
    val allBarangs: LiveData<List<Barang>> = _allBarangs


    private val _navigateToHome = MutableLiveData<Event<Boolean>>()
    val navigateToHome: LiveData<Event<Boolean>>
        get() = _navigateToHome

    /**
     * Section Barang LiveData
     */
    private val _idBarang = MutableLiveData<Int>()
    val idBarang: LiveData<Int> = _idBarang

    private val _namaBarang = MutableLiveData<String>()
    val namaBarang: LiveData<String> = _namaBarang

    private val _merkBarang = MutableLiveData<String>()
    val merkBarang: LiveData<String> = _merkBarang

    private val _ketBarang = MutableLiveData<String>()
    val ketBarang: LiveData<String> = _ketBarang


    fun getAllBarang() {
        _dataLoading.value = true
        viewModelScope.launch {
            val barangs = barangRepository.getAllBarang()
            _allBarangs.value = barangs
            _dataLoading.value = false
        }
    }

    fun getDetailBarang(idBarang: Int) {
        if (idBarang != 0) {
            _dataLoading.value = true
            viewModelScope.launch {
                val barang = barangRepository.getDetailBarang(idBarang)
                _idBarang.value = barang.Id
                _namaBarang.value = barang.Nama
                _merkBarang.value = barang.Merk
                _ketBarang.value = barang.Keterangan
                _dataLoading.value = false
            }
        }
    }

    fun validateBarang() {
        _dataLoading.value = true
        if (namaBarang.value.isNullOrEmpty()) {
            _errorMessage.value = "Nama barang tidak boleh kosong"
            _dataLoading.value = false
            return
        }
        if (merkBarang.value.isNullOrEmpty()) {
            _errorMessage.value = "Merk barang tidak boleh kosong"
            _dataLoading.value = false
            return
        }

        try {
            _errorMessage.value = ""
            simpanBarang()
            _navigateToHome.value = Event(true)
        } catch (e: Exception) {
            _errorMessage.value = "Gagal simpan barang : $e"
        }
    }

    fun simpanBarang() {
        val idBarang = idBarang.value?.toInt()
        val namaBarang = namaBarang.value!!
        val merkBarang = merkBarang.value!!
        val ketBarang = ketBarang.value ?: ""
        val dataBarang = BarangEntity(
            Id = idBarang ?: 0,
            Nama = namaBarang,
            Merk = merkBarang,
            Keterangan = ketBarang
        )
        viewModelScope.launch {
            barangRepository.insertOrUpdateBarang(dataBarang)
        }
    }

    fun deleteBarang(idBarang: Int) {
        viewModelScope.launch {
            barangRepository.deleteBarang(idBarang)
            _navigateToHome.value = Event(true)
            _dataLoading.value = false
        }
    }

    fun editTextNamaChanged(newText: String?) {
        if (newText == namaBarang.value) {
            return
        } else {
            _namaBarang.value = newText
        }
    }

    fun editTextMerkChanged(newText: String?) {
        if (newText == merkBarang.value) {
            return
        } else {
            _merkBarang.value = newText
        }
    }

    fun editTextKetChanged(newText: String?) {
        if (newText == ketBarang.value) {
            return
        } else {
            _ketBarang.value = newText
        }
    }

    fun clearLiveDataBarang() {
        _namaBarang.value = ""
        _merkBarang.value = ""
        _ketBarang.value = ""
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("StokViewModel", "StokViewModel destroyed")
    }

}