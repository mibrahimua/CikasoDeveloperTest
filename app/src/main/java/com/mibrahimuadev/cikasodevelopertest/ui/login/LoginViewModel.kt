package com.mibrahimuadev.cikasodevelopertest.ui.login

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences.Editor
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mibrahimuadev.cikasodevelopertest.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "LoginViewModel"
    private val userRepository: UserRepository
    val editor: Editor
    init {
        userRepository = UserRepository(application)
        editor = application.getSharedPreferences("LoginDetail", MODE_PRIVATE).edit()
    }

    val _isValidated = MutableLiveData<Boolean>()
    val isValidated: LiveData<Boolean> = _isValidated

    val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun validateUser(userName: String, userPassword: String) {
        if (!userName.isNullOrEmpty() && !userPassword.isNullOrEmpty()) {
            viewModelScope.launch {
                val job1 = async(Dispatchers.Main) {
                    userRepository.validateUser(userName, userPassword)
                }
                val userDetail = job1.await()
                Log.i("LoginViewModel", "userDetail : $userDetail")
                if (userDetail == null) {
                    _isValidated.value = false
                    _errorMessage.value = "User Not Found"
                } else {
                    editor.putString("userName", userDetail.Username)
                    editor.apply()

                    _isValidated.value = true
                }

            }
        } else {
            _isValidated.value = false
            _errorMessage.value = "Username or Password cannot be empty"
        }
    }


}