package com.mibrahimuadev.cikasodevelopertest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mibrahimuadev.cikasodevelopertest.R
import com.mibrahimuadev.cikasodevelopertest.data.model.Barang
import com.mibrahimuadev.cikasodevelopertest.ui.barang.StokFragmentDirections

class StokBarangListAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<StokBarangListAdapter.StokViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val barangs = mutableListOf<Barang>()
    private val barangsCopy = mutableListOf<Barang>()

    inner class StokViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {
        val namaBarang: TextView = itemVIew.findViewById(R.id.namaBarang)
        val merkBarang: TextView = itemVIew.findViewById(R.id.merkBarang)
        val ketBarang: TextView = itemVIew.findViewById(R.id.ketBarang)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StokViewHolder {
        val itemVIew = inflater.inflate(R.layout.recyclerview_stok, parent, false)
        return StokViewHolder(itemVIew)
    }

    internal fun setBarang(barang: List<Barang>) {
        this.barangs.removeAll(barangs)
        this.barangsCopy.removeAll(barangsCopy)
        this.barangs.addAll(barang)
        this.barangsCopy.addAll(barang)
        notifyDataSetChanged()
    }

    internal fun setFilter(filterText: String?) {
        barangs.clear()
        if (filterText?.isEmpty()!!) {
            barangs.addAll(barangsCopy)
        } else {
            for (barang in barangsCopy) {
                if (barang.Nama.toLowerCase().contains(filterText.toLowerCase())
                    || barang.Merk.toLowerCase().contains(filterText.toLowerCase())
                    || barang.Keterangan.toLowerCase().contains(filterText.toLowerCase())
                ) {
                    barangs.add(barang)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: StokViewHolder, position: Int) {
        val current = barangs[position]
        holder.namaBarang.text = current.Nama
        holder.merkBarang.text = current.Merk
        holder.ketBarang.text = current.Keterangan
        holder.itemView.setOnClickListener { view ->
            val action = StokFragmentDirections.actionStokFragmentToAddEditStokFragment()
                .setIdBarang(current.Id)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return barangs.size
    }



}