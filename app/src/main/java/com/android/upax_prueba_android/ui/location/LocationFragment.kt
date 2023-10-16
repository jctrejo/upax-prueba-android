package com.android.upax_prueba_android.ui.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.core.Constants.COLLECTION_ADDRESS_KEY
import com.android.upax_prueba_android.core.Constants.COLLECTION_LATITUDE_KEY
import com.android.upax_prueba_android.core.Constants.COLLECTION_LOCATION_KEY
import com.android.upax_prueba_android.core.Constants.COLLECTION_LONGITUDE_KEY
import com.android.upax_prueba_android.core.Constants.ERROR
import com.android.upax_prueba_android.core.Constants.ZOOM_10
import com.android.upax_prueba_android.databinding.FragmentLocationMapsBinding
import com.android.upax_prueba_android.util.LoadingDialog
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.common.io.Files.map
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationMapsBinding
    private lateinit var contextBinding: Context
    private val db = FirebaseFirestore.getInstance()
    private val args by navArgs<LocationFragmentArgs>()

    private val callback = OnMapReadyCallback { googleMap ->
        db.collection(COLLECTION_ADDRESS_KEY).document(COLLECTION_LOCATION_KEY).get()
            .addOnSuccessListener { document ->
                document?.let {
                    val lat = document.getString(COLLECTION_LATITUDE_KEY)?.toDouble()
                    val long = document.getString(COLLECTION_LONGITUDE_KEY)?.toDouble()
                    val location = if (lat != null && long != null) {
                        LatLng(lat ?: 0.0, long ?: 0.0)
                    } else {
                        LatLng(args.latitude.toDouble(), args.longitude.toDouble())
                    }
                    googleMap.addMarker(MarkerOptions().position(location).title("$lat $long"))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, ZOOM_10))
                    //loader.dismiss()
                }
            }.addOnFailureListener { error ->
                Log.e(ERROR, error.toString())
            }
    }

    private lateinit var loader: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return if (this::binding.isInitialized) {
            binding.root
        } else {
            binding = FragmentLocationMapsBinding.inflate(layoutInflater)
            contextBinding = binding.root.context
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}