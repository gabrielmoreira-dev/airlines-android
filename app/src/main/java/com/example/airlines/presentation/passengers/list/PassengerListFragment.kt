package com.example.airlines.presentation.passengers.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.airlines.R
import com.example.airlines.databinding.FragmentPassengerListBinding
import com.example.airlines.presentation.common.BaseFragment
import com.example.airlines.presentation.common.State
import com.example.airlines.presentation.common.UIString
import com.example.airlines.presentation.passengers.list.adapters.PassengerAdapter
import com.example.airlines.presentation.passengers.list.models.PassengerPM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerListFragment: BaseFragment<FragmentPassengerListBinding>(
    FragmentPassengerListBinding::inflate
) {
    private val viewModel by viewModels<PassengerListViewModel>()
    private lateinit var passengerAdapter: PassengerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.state.observe(this) {
            when (it) {
                is State.Success -> {
                    handleSuccessState(it.model)
                }
                is State.Loading -> {
                    handleLoadingState()
                }
                is State.Error -> {
                    handleErrorState(it.message)
                }
            }
        }
        binding.errorView.setOnTryAgainListener {
            viewModel.getPassengerList()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPassengerList()
    }

    private fun setupRecyclerView() {
        passengerAdapter = PassengerAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = passengerAdapter
        }
    }

    private fun handleSuccessState(passengerList: List<PassengerPM>) {
        passengerAdapter.setupItems(passengerList)
        binding.apply {
            loadingView.dismiss()
            errorView.dismiss()
        }
    }

    private fun handleLoadingState() {
        binding.apply {
            loadingView.show()
            errorView.dismiss()
        }
    }

    private fun handleErrorState(message: UIString) {
        val description = context?.let {
            message.asString(it)
        } ?: getString(R.string.generic_error_message)
        binding.apply {
            errorView.setDescription(description)
            errorView.show()
            loadingView.dismiss()
        }
    }
}