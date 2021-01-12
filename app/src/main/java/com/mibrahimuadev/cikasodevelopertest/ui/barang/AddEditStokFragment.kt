package com.mibrahimuadev.cikasodevelopertest.ui.barang

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.text.set
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mibrahimuadev.cikasodevelopertest.databinding.FragmentAddEditStokBinding
import com.mibrahimuadev.cikasodevelopertest.utils.EventObserver

class AddEditStokFragment : Fragment() {
    private var _binding: FragmentAddEditStokBinding? = null
    private val binding get() = _binding!!
    private val args: AddEditStokFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditStokBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewmodelFactory = StokViewModelFactory(application)
        val stokViewModel =
            ViewModelProvider(this, viewmodelFactory).get(StokViewModel::class.java)
        stokViewModel.getDetailBarang(args.idBarang)
        stokViewModel.dataLoading.observe(viewLifecycleOwner) { loading ->
            if (loading == false) {
                binding.etNamaBarang.setText(stokViewModel.namaBarang.value)
                binding.etMerkBarang.setText(stokViewModel.merkBarang.value)
                binding.etKetBarang.setText(stokViewModel.ketBarang.value)
            }
        }

        binding.etNamaBarang.addTextChangedListener {
            stokViewModel.editTextNamaChanged(it.toString())
        }

        binding.etMerkBarang.addTextChangedListener {
            stokViewModel.editTextMerkChanged(it.toString())
        }

        binding.etKetBarang.addTextChangedListener {
            stokViewModel.editTextKetChanged(it.toString())
        }

        binding.btnSave.setOnClickListener {
            /**
             * Close keyboard before navigate
             */
            val imm =
                getActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.getWindowToken(), 0)
            stokViewModel.validateBarang()
            stokViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
                if (error.isNotEmpty()) {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            }
            stokViewModel.navigateToHome.observe(viewLifecycleOwner, EventObserver {
                val action =
                    AddEditStokFragmentDirections.actionAddEditStokFragmentToStokFragment()
                findNavController().navigate(action)
            })
        }

        binding.btnDelete.isVisible = args.idBarang != 0
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Warning")
                .setMessage("Hapus barang ini ?")
                .setNegativeButton("Batal") { _, _ ->
                }
                .setPositiveButton("Hapus") { _, _ ->
                    stokViewModel.deleteBarang(args.idBarang)
                    stokViewModel.navigateToHome.observe(viewLifecycleOwner) {
                        val action =
                            AddEditStokFragmentDirections.actionAddEditStokFragmentToStokFragment()
                        findNavController().navigate(action)
                    }
                }
                .show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}