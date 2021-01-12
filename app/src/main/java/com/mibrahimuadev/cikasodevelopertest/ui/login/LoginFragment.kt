package com.mibrahimuadev.cikasodevelopertest.ui.login

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.mibrahimuadev.cikasodevelopertest.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewmodelFactory = LoginViewModelFactory(application)
        val loginViewModel =
            ViewModelProvider(this, viewmodelFactory).get(LoginViewModel::class.java)

        binding.btnLogin.setOnClickListener {
            /**
             * Close keyboard before navigate
             */
            val imm =
                getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(), 0)

            val userName = binding.userName.text.toString()
            val userPassword = binding.userPassword.text.toString()
            loginViewModel.validateUser(userName, userPassword)
        }

        loginViewModel.isValidated.observe(viewLifecycleOwner) {
            if (it) {
                Navigation.findNavController(requireView())
                    .navigate(LoginFragmentDirections.actionLoginFragmentToStokFragment())
            }
        }
        loginViewModel.errorMessage.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                loginViewModel._errorMessage.value = null
            }
        }
        checkSignedAccount()
        return binding.root
    }

    fun checkSignedAccount() {
        val prefs: SharedPreferences =
            requireActivity().getSharedPreferences("LoginDetail", MODE_PRIVATE)
        val userName = prefs.getString("userName", "empty")

        if(!userName.equals("empty")) {
            val action = LoginFragmentDirections.actionLoginFragmentToStokFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}