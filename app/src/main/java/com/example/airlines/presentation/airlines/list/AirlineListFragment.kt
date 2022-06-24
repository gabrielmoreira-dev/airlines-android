package com.example.airlines.presentation.airlines.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.airlines.data.remote.data_source.AirlineRDS
import com.example.airlines.data.repository.AirlineRepository
import com.example.airlines.databinding.FragmentAirlineListBinding
import com.example.airlines.presentation.airlines.list.adapters.AirlineAdapter
import com.example.airlines.presentation.common.BaseFragment
import com.example.airlines.presentation.common.State
import com.example.domain.use_case.GetAirlineListUC

class AirlineListFragment : BaseFragment<FragmentAirlineListBinding>(
    FragmentAirlineListBinding::inflate
) {
    private lateinit var viewModel: AirlineListViewModel
    private lateinit var airlineAdapter: AirlineAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val useCase = GetAirlineListUC(AirlineRepository(AirlineRDS.getInstance()))
        viewModel = ViewModelProvider(
            this,
            AirlineListViewModelFactory(useCase)
        ).get(AirlineListViewModel::class.java)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.state.observe(this) {
            when (it) {
                is State.Success -> {
                    airlineAdapter.setupItems(it.model)
                }
                is State.Loading -> {
                    // TODO: Handle loading state
                }
                is State.Error -> {
                    // TODO: Handle error state
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
}
