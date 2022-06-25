package com.example.airlines.presentation.airlines.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.airlines.databinding.FragmentAirlineListBinding
import com.example.airlines.presentation.airlines.list.adapters.AirlineAdapter
import com.example.airlines.presentation.airlines.list.models.AirlinePM
import com.example.airlines.presentation.common.BaseFragment
import com.example.airlines.presentation.common.State
import com.example.airlines.presentation.common.UIString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AirlineListFragment : BaseFragment<FragmentAirlineListBinding>(
    FragmentAirlineListBinding::inflate
) {
    private val viewModel by viewModels<AirlineListViewModel>()
    private lateinit var airlineAdapter: AirlineAdapter

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
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAirlineList()
    }

    private fun setupRecyclerView() {
        airlineAdapter = AirlineAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = airlineAdapter
        }
    }

    private fun handleSuccessState(airlineList: List<AirlinePM>) {
        airlineAdapter.setupItems(airlineList)
    }

    private fun handleLoadingState() {
        // TODO: Handle loading state
    }

    private fun handleErrorState(message: UIString) {
        // TODO: Handle error state
    }
}
