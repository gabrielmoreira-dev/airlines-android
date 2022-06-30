package com.example.airlines.presentation.passengers.list.adapters

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.airlines.R
import com.example.airlines.databinding.ResPassengerItemBinding
import com.example.airlines.presentation.common.BaseAdapter
import com.example.airlines.presentation.passengers.list.models.PassengerPM

class PassengerAdapter(
    onItemClicked: ((PassengerPM) -> Unit)? = null
) : BaseAdapter<PassengerPM, ResPassengerItemBinding>(
    ResPassengerItemBinding::inflate,
    onItemClicked
) {
    override fun bindItem(context: Context, binding: ResPassengerItemBinding, position: Int) {
        val passenger = items[position]
        binding.title.text = passenger.name
        binding.description.text = passenger.age.asString(context)
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
        Glide.with(context)
            .applyDefaultRequestOptions(requestOptions)
            .load(passenger.photoUrl)
            .into(binding.photoImage)
    }
}