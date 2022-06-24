package com.example.airlines.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.airlines.databinding.ActivityMainBinding
import com.example.airlines.presentation.airlines.list.AirlineListFragment
import com.example.airlines.presentation.common.BaseActivity
import com.example.airlines.presentation.passengers.list.PassengerListFragment

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            callAirlineListFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.bottomNavigation.setOnItemSelectedListener {
            onNavigationItemSelected(it)
        }
    }

    private fun onNavigationItemSelected(item: MenuItem): Boolean {
        val menu = binding.bottomNavigation.menu
        return when (item.itemId) {
            menu.getItem(0).itemId -> {
                callAirlineListFragment()
                true
            }
            menu.getItem(1).itemId -> {
                callPassengerListFragment()
                true
            }
            else -> false
        }
    }

    private fun callAirlineListFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<AirlineListFragment>(binding.fragmentContainerView.id)
        }
    }

    private fun callPassengerListFragment() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<PassengerListFragment>(binding.fragmentContainerView.id)
        }
    }
}