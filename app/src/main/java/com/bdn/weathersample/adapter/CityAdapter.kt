package com.bdn.weathersample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bdn.weathersample.R
import com.bdn.weathersample.database.model.CityModel
import com.bdn.weathersample.viewmodel.HomeViewModel

class CityAdapter (private val chatViewModel: HomeViewModel) :
    RecyclerView.Adapter<CityViewHolder>() {

    private val chatList = ArrayList<CityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_city,
                parent,
                false
            )
        )

    override fun getItemCount() = chatList.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bindTo(chatList[position], chatViewModel)
    }

    fun updateData(items: List<CityModel>) {
        this.chatList.clear()
        this.chatList.addAll(items)
        notifyDataSetChanged()
    }
}