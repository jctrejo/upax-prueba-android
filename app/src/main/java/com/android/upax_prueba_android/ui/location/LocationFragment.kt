package com.android.upax_prueba_android.ui.location

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.upax_prueba_android.databinding.FragmentLocationBinding

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var contextBinding: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return if (this::binding.isInitialized) {
            binding.root
        } else {
            binding = FragmentLocationBinding.inflate(layoutInflater)
            contextBinding = binding.root.context
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {

    }
}