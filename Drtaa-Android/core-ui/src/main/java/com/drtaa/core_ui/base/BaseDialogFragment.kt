package com.drtaa.core_ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.drtaa.core_ui.R
import com.drtaa.core_ui.component.LoadingDialog

abstract class BaseDialogFragment<B : ViewDataBinding>(private val layoutResId: Int) :
    DialogFragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!
    protected var isBackPressedEnabled = true

    private val loading by lazy {
        LoadingDialog(requireActivity())
    }

    fun dismissLoading() {
        loading.dismiss()
    }

    fun showLoading() {
        loading.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.FullScreenDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.attributes?.windowAnimations = R.style.DialogNoAnimation

        dialog.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
                // isBackPressedEnabled가 false일 때만 뒤로가기 막음
                return@setOnKeyListener !isBackPressedEnabled
            }
            false
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)

    fun navigateDestination(@IdRes action: Int) { // Navigation 이동
        findNavController().navigate(action)
    }

    fun navigateDestination(action: NavDirections) { // Navigation 이동
        findNavController().navigate(action)
    }

    fun navigatePopBackStack() { // 뒤로 가기
        findNavController().popBackStack()
    }

    fun navigateDestinationBundle(@IdRes action: Int, bundle: Bundle) {
        findNavController().navigate(action, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}