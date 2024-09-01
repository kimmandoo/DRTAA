package com.drtaa.feature_mypage

import androidx.fragment.app.viewModels
import com.drtaa.core_ui.base.BaseFragment
import com.drtaa.feature_mypage.databinding.FragmentMyPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageFragment: BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {
    private val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            binding.viewModel = this@MyPageFragment.viewModel
        }
    }
}