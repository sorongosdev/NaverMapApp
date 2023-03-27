package com.sorongos.navermapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.Tm128
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.sorongos.navermapapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var naverMap: NaverMap
    private var isMapInit = false

    private var restaurantListAdapter = RestaurantListAdapter{

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapView.onCreate(savedInstanceState)

        binding.mapView.getMapAsync(this)

        //include viewBinding
        binding.bottomSheetLayout.searchResultRecyclerView.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantListAdapter
        }

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query?.isNotEmpty() == true) {
                    SearchRepository.getGoodRestaurant(query)
                        .enqueue(object : Callback<SearchResult> {
                            override fun onResponse(
                                call: Call<SearchResult>,
                                response: Response<SearchResult>
                            ) {
                                val searchItemList = response.body()?.items.orEmpty()

                                if (searchItemList.isEmpty()) {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "검색 결과 없음",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Log.e("searchItemList","검색 결과 없음")

                                    return
                                }
                                // 맵이 초기화가 되지 않았다면
                                else if (isMapInit.not()) {
                                    Toast.makeText(this@MainActivity, "오류 발생", Toast.LENGTH_SHORT)
                                        .show()
                                    Log.e("isMapInit","오류 발생")

                                    return
                                }

                                //marker
                                val markers = searchItemList.map {
                                    Marker(
                                        Tm128(
                                            it.mapx.toDouble(),
                                            it.mapy.toDouble()
                                        ).toLatLng()
                                    ).apply {
                                        captionText = it.title
                                        map = naverMap // 초기화가 끝난 후에
                                    }
                                }

                                Log.e("markers",markers.toString())

                                // move camera to the first result
                                // markers.first().position : non-nullable, already checked
                                restaurantListAdapter.setData(searchItemList)
                                restaurantListAdapter.notifyItemRangeChanged(0,searchItemList.size)

                                val cameraUpdate = CameraUpdate.scrollTo(markers.first().position)
                                    .animate(CameraAnimation.Easing)
                                naverMap.moveCamera(cameraUpdate)
                            }

                            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                    false // 키보드가 내려감
                } else {
                    true // 검색어가 없음, 키보드 안 내려감, 사용중
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        //Can change mapType.
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    /**지도 호출시 호출*/
    override fun onMapReady(mapObject: NaverMap) {
        naverMap = mapObject
        isMapInit = true

//        //can use move camera...etc, after getting object here.
//        val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.2974, 126.8356))
//            .animate(CameraAnimation.Easing)
//        naverMap.moveCamera(cameraUpdate)

    }
}