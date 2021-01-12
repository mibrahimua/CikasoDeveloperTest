package com.mibrahimuadev.cikasodevelopertest.ui.barang

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mibrahimuadev.cikasodevelopertest.R
import com.mibrahimuadev.cikasodevelopertest.adapter.StokBarangListAdapter
import com.mibrahimuadev.cikasodevelopertest.databinding.FragmentStokBinding

class StokFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentStokBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StokBarangListAdapter
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStokBinding.inflate(layoutInflater)
        val application = requireActivity().application
        val viewmodelFactory = StokViewModelFactory(application)
        val stokViewModel =
            ViewModelProvider(this, viewmodelFactory).get(StokViewModel::class.java)
        val recyclerView = binding.recycleviewBarang
        adapter = StokBarangListAdapter(application)
        stokViewModel.clearLiveDataBarang()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(application)

        stokViewModel.getAllBarang()
        stokViewModel.allBarangs.observe(viewLifecycleOwner) {
            adapter.setBarang(it)
        }

        binding.btnTambahBarang.setOnClickListener {
            val action = StokFragmentDirections.actionStokFragmentToAddEditStokFragment()
            findNavController().navigate(action)
        }

        setHasOptionsMenu(true)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        // Get the SearchView and set the searchable configuration
        val searchItem = menu.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.onActionViewExpanded()
        searchView.clearFocus()
        searchView.queryHint = "Cari barang"
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_logout) {

            val editor = requireActivity().getSharedPreferences("LoginDetail", Context.MODE_PRIVATE).edit()
            editor.putString("userName", "empty")
            editor.apply()

            val action = StokFragmentDirections.actionStokFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.setFilter(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.setFilter(newText)
        return true
    }

    override fun onDetach() {
        super.onDetach()
        searchView.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}