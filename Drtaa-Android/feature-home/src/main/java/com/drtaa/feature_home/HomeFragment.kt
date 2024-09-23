package com.drtaa.feature_home

import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.drtaa.core_ui.base.BaseFragment
import com.drtaa.feature_home.databinding.FragmentHomeBinding
import com.drtaa.feature_home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeviewModel: HomeViewModel by viewModels()

    override fun initView() {
        binding.apply {
            binding.homeviewModel = this@HomeFragment.homeviewModel
        }
        initObserve()
        initEvent()
    }

    private fun initObserve() {
        homeviewModel.currentUser.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { result ->
                if (result == null) return@onEach
                binding.socialUser = result
                Timber.d("$result")
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initEvent() {
        binding.btnHomeCarRent.setOnClickListener {
            navigateDestination(R.id.action_home_to_rent)
        }

        binding.btnHomeRentHistory.setOnClickListener {
            navigateDestination(R.id.action_homeFragment_to_rentHistoryFragment)
        }
    }
}