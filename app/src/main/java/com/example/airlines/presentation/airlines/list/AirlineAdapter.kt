package com.example.airlines.presentation.airlines.list

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.airlines.R
import com.example.airlines.databinding.ResAirlineItemBinding
import com.example.airlines.presentation.airlines.list.models.Airline
import com.example.airlines.presentation.common.BaseAdapter

class AirlineAdapter(onItemClicked: ((Airline) -> Unit)? = null)
    : BaseAdapter<Airline, ResAirlineItemBinding>(
    ResAirlineItemBinding::inflate,
    onItemClicked
) {
    override fun bindItem(
        context: Context,
        binding: ResAirlineItemBinding,
        position: Int
    ) {
        val airline = items[position]
        binding.title.text = airline.name
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
        Glide.with(context)
            .applyDefaultRequestOptions(requestOptions)
            .load(airline.logoUrl)
            .into(binding.logoImage)
    }
}