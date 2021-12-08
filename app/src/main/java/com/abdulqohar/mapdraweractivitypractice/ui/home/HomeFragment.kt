package com.abdulqohar.mapdraweractivitypractice.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.abdulqohar.mapdraweractivitypractice.R
import com.abdulqohar.mapdraweractivitypractice.UserX
import com.abdulqohar.mapdraweractivitypractice.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var mMap: GoogleMap
    private lateinit var usersList: ArrayList<UserX>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
//        bindJsonDataInFacilityList()
//        Log.d("user", usersList.toString())
//        val jsonObject = JSONObject(jsonDataFromAssets()!!)
//        val jsonArray = jsonObject.getJSONArray("users")
//        Log.d("users", jsonArray.toString())
    }

    private fun jsonDataFromAssets(): String? {
        var json:String? = null
        return requireContext().assets.open("user.json").bufferedReader().use {
            it.readText()
        }
    }

    private fun bindJsonDataInFacilityList(){
        usersList = arrayListOf()
        val userJsonArray = JSONArray(jsonDataFromAssets())
        for (i in 0 until userJsonArray.length()){
            val user = UserX()
            val currentUserObject = userJsonArray.getJSONObject(i)
            user.name = currentUserObject.getString("name")
            user.id = currentUserObject.getString("id")
            user.age = currentUserObject.getString("age")
            usersList.add(user)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}