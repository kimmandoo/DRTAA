package com.drtaa.feature_taxi

import android.location.Geocoder
import android.os.Build
import android.view.KeyEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.drtaa.core_map.LOCATION_PERMISSION_REQUEST_CODE
import com.drtaa.core_map.base.BaseMapFragment
import com.drtaa.core_map.moveCameraTo
import com.drtaa.core_map.setCustomLocationButton
import com.drtaa.core_model.map.Search
import com.drtaa.core_ui.hideKeyboard
import com.drtaa.core_ui.showSnackBar
import com.drtaa.feature_taxi.adapter.TaxiSearchListAdapter
import com.drtaa.feature_taxi.databinding.FragmentTaxiSearchBinding
import com.drtaa.feature_taxi.viewmodel.TaxiSearchViewModel
import com.drtaa.feature_taxi.viewmodel.TaxiViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class TaxiSearchFragment :
    BaseMapFragment<FragmentTaxiSearchBinding>(R.layout.fragment_taxi_search) {
    private val taxiViewModel: TaxiViewModel by hiltNavGraphViewModels(R.id.nav_graph_taxi)
    private val taxiSearchViewModel: TaxiSearchViewModel by viewModels()
    private val args: TaxiSearchFragmentArgs by navArgs()

    override var mapView: MapView? = null

    private val taxiSearchListAdapter = TaxiSearchListAdapter()

    private lateinit var naverMap: NaverMap

    private lateinit var locationSource: FusedLocationSource
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>


    override fun initMapView() {
        mapView = binding.mvTaximap
        mapView?.getMapAsync(this)
    }

    override fun initOnMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.uiSettings.isLocationButtonEnabled = false
        naverMap.setCustomLocationButton(binding.layoutTaxiSearchBottomSheet.ivTaxiSearchCurrentLocation)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        initObserve(naverMap)
    }

    override fun iniView() {
        initBottomSheet()
        initEvent()
        initObserve()
        initRV()
    }

    private fun initRV() {
        taxiSearchListAdapter.setItemClickListener(object :
            TaxiSearchListAdapter.ItemClickListener {
            override fun onItemClicked(search: Search) {
//                taxiSearchViewModel.setSelectedSearchItem(search)
                naverMap.moveCameraTo(search.lat, search.lng)
                naverMap.setContentPadding(0, 0, 0, MAP_BOTTOM_CONTENT_PADDING)
            }
        })

        binding.layoutTaxiSearchBottomSheet.rvSearchResult.adapter = taxiSearchListAdapter
    }

    private fun initEvent() {
        binding.layoutTaxiSearchBottomSheet.apply {
            ivSearchLocation.setOnClickListener {
                if (etSearchLocation.text.isEmpty()) {
                    showSnackBar("장소를 입력해주세요.")
                    return@setOnClickListener
                }
                taxiSearchViewModel.getSearchList(etSearchLocation.text.toString())
                requireActivity().hideKeyboard(requireView())
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            etSearchLocation.setOnKeyListener { _, keyCode, keyEvent ->
                var handle = false
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    handle = true
                    ivSearchLocation.callOnClick()
                }
                handle
            }

            btnSearchSelect.setOnClickListener {
                var center = naverMap.cameraPosition.target
                taxiSearchViewModel.getReverseGeocode(center.latitude, center.longitude)
                taxiSearchViewModel.setSelectedSearchItem(Search(taxiSearchViewModel.reverseGeocode.toString(), "", "", center.longitude, center.latitude))
                if (taxiSearchViewModel.selectedSearchItem.value != null) {
                    if (args.isStartLocation) {
                        taxiViewModel.setTaxiStartLocation(taxiSearchViewModel.selectedSearchItem.value!!)
                    } else {
                        taxiViewModel.setTaxiEndLocation(taxiSearchViewModel.selectedSearchItem.value!!)
                    }
                    navigatePopBackStack()
                } else {
                    showSnackBar("장소를 선택해주세요")
                }
            }
        }
    }

    private fun getAddress(center: LatLng) {

        showLoading()

        val geocoder = Geocoder(requireContext(), Locale.KOREA)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(center.latitude, center.longitude, 1) { address ->
                if (address.isNotEmpty()) {
                    taxiSearchViewModel.setSelectedSearchItem(
                        Search(
                            address[0].getAddressLine(0).toString(),
                            address[0].thoroughfare.toString(),
                            address[0].subThoroughfare.toString(),
                            center.longitude,
                            center.latitude
                        )
                    )
                }
            }
        } else {
            val address = geocoder.getFromLocation(center.latitude, center.longitude, 1)
            if (address != null) {
                taxiSearchViewModel.setSelectedSearchItem(
                    Search(
                        address[0].getAddressLine(0).toString(),
                        address[0].thoroughfare.toString(),
                        address[0].subThoroughfare.toString(),
                        center.longitude,
                        center.latitude
                    )
                )
            }
        }

        dismissLoading()

    }


    private fun initObserve(naverMap: NaverMap) {
        taxiSearchViewModel.selectedSearchItem.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { seleted ->
                seleted?.let {
//                    naverMap.setMarker(seleted.lat, seleted.lng)
//                    naverMap.moveCameraTo(seleted.lat, seleted.lng)
//                    naverMap.setContentPadding(0, 0, 0, MAP_BOTTOM_CONTENT_PADDING)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        taxiSearchViewModel.reverseGeocode.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { result ->
                result?.let {
                    Timber.d("주소는?? ${result}")
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initObserve() {
        taxiSearchViewModel.searchList.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { result ->
                result.onSuccess { data ->
                    binding.layoutTaxiSearchBottomSheet.tvSearchBefore.visibility = View.GONE
                    taxiSearchListAdapter.submitList(data)
                    binding.apply {
                        if (data.isEmpty()) {
                            layoutTaxiSearchBottomSheet.tvSearchNothing.visibility =
                                View.VISIBLE
                            layoutTaxiSearchBottomSheet.btnSearchSelect.visibility = View.GONE
                        } else {
                            layoutTaxiSearchBottomSheet.tvSearchNothing.visibility = View.GONE
                            layoutTaxiSearchBottomSheet.btnSearchSelect.visibility =
                                View.VISIBLE
                        }
                    }
                }.onFailure {
                    showSnackBar("오류가 발생했습니다.")
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initBottomSheet() {
        binding.layoutTaxiSearchBottomSheet.tvSearchNothing.visibility = View.GONE
        binding.layoutTaxiSearchBottomSheet.btnSearchSelect.visibility = View.GONE

        behavior = BottomSheetBehavior.from(binding.clTaxiSearchBottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        behavior.isHideable = false

        behavior.peekHeight = BOTTOM_SHEET_PEEK_HEIGHT
        behavior.maxHeight = BOTTOM_SHEET_PEEK_HEIGHT * 2

        behavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Timber.d("STATE_EXPANDED 펼침")
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Timber.d("STATE_COLLAPSED 접음")
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Timber.d("STATE_DRAGGING 드래그")
                    }

                    BottomSheetBehavior.STATE_SETTLING -> {
                        Timber.d("STATE_SETTLING 고정")
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Timber.d("STATE_HIDDEN 숨김")
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    companion object {
        const val BOTTOM_SHEET_PEEK_HEIGHT = 500
        const val MAP_BOTTOM_CONTENT_PADDING = 100
    }
}

