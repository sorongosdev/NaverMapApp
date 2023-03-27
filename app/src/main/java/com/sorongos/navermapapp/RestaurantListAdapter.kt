package com.sorongos.navermapapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import com.sorongos.navermapapp.databinding.ItemRestaurantBinding

class RestaurantListAdapter(private val onClick: (LatLng) -> Unit): RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ItemRestaurantBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SearchItem){
            binding.titleTextView.text = item.title
            binding.categoryTextView.text = item.category
            binding.locationTextView.text = item.roadAddress

            binding.root.setOnClickListener {
                onClick(Tm128(item.mapx.toDouble(), item.mapy.toDouble()).toLatLng())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}