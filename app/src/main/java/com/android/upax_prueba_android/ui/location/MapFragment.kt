package com.android.upax_prueba_android.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.core.Constants.COLLECTION_ADDRESS_KEY
import com.android.upax_prueba_android.core.Constants.PERMISSION_ID
import com.android.upax_prueba_android.core.Constants.REQUEST_CODE_LOCATION
import com.android.upax_prueba_android.core.Constants.SPACE
import com.android.upax_prueba_android.databinding.FragmentLocationUserBinding
import com.android.upax_prueba_android.util.extension.glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MapFragment : Fragment(R.layout.fragment_location_user) {

    private lateinit var binding: FragmentLocationUserBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var map: GoogleMap? = null
    private var userMarker: Marker? = null
    private val db = FirebaseFirestore.getInstance()
    private var handler: Handler = Handler()
    private var lat: Double? = 0.0
    private var long: Double? = 0.0
    private var isPause: Boolean? = false
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        MapsInitializer.initialize(requireActivity())
        setupMarker(lat ?: 0.0, long ?: 0.0, "UbiPokemon")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationUserBinding.bind(view)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.btnGetLocation.setOnClickListener {
            isPause = if (isPause == true) {
                binding.btnGetLocation.glide(R.drawable.ic_play)
                handler.removeCallbacksAndMessages(null)
                false
            } else {
                binding.btnGetLocation.glide(R.drawable.ic_pause)
                runLocationSend()
                true
            }
        }
        runLocationSend()
        getLastLocation()

        binding.btnViewLocation.setOnClickListener {
            findNavController().navigate(
                MapFragmentDirections.actionLocationUserFragmentToLocationMapsFragment(
                    lat?.toFloat() ?: 0.0F, long?.toFloat() ?: 0.0F
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        setupMap()
    }

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun setupMarker(long: Double, lat: Double, name: String?) {
        val latLng = LatLng(lat, long)

        if (userMarker != null) {
            userMarker?.remove()
        }
        userMarker = map?.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
        )

        val cameraPosition = CameraPosition.Builder().target(LatLng(lat, long)).zoom(14.0f).build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map?.moveCamera(cameraUpdate)
    }

    private fun runLocationSend() {
        binding.btnGetLocation.glide(R.drawable.ic_pause)

        handler.postDelayed(
            {
                CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                    withContext(Dispatchers.Main) {
                        getLocation()
                    }
                }
            },
            1000
        )
    }

    private fun getLocation() {
        val lastLocation = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION
            )
            return
        }

        lastLocation.addOnSuccessListener {
            if (it != null) {
                binding.txtLat.text = it.latitude.toString()
                binding.txtLng.text = it.longitude.toString()
                val randomString = UUID.randomUUID().toString().substring(0, 15)

                db.collection(COLLECTION_ADDRESS_KEY).document(randomString)
                    .set(GPS(it.latitude.toString(), it.longitude.toString()))
                    .addOnSuccessListener {
                        Toast.makeText(
                            requireContext(),
                            getText(R.string.save_location),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(
                            "Firebase",
                            "Error writing document",
                            e
                        )
                    }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        println("Error: location")
                    } else {
                        lat = location.latitude
                        long = location.longitude
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    data class GPS(val latitude: String = SPACE, val longitude: String = SPACE)
}
