package com.android.upax_prueba_android.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.upax_prueba_android.util.extension.colorImage
import com.android.upax_prueba_android.util.extension.colorText
import com.android.upax_prueba_android.util.extension.glidePlaceholder
import com.android.upax_prueba_android.util.extension.validateImageProfile
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.core.AppUiState
import com.android.upax_prueba_android.data.model.ProfileModel
import com.android.upax_prueba_android.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var contextBinding: Context
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return if (this::binding.isInitialized) {
            binding.root
        } else {
            binding = FragmentProfileBinding.inflate(layoutInflater)
            contextBinding = binding.root.context
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        observer()
        sentButton.setOnClickListener {
            viewModel.getProfile(nameEditText.text.toString(), urlEditText.text.toString())
        }

        insectImageView.setOnClickListener { changePlaceholder(R.drawable.ic_avatar_insect) }
        japaneseImageView.setOnClickListener { changePlaceholder(R.drawable.ic_avatar_japanese) }
        monsterImageView.setOnClickListener { changePlaceholder(R.drawable.ic_avatar_monster) }
        batmanImageView.setOnClickListener { changePlaceholder(R.drawable.ic_avatar_batman) }
        jokerImageView.setOnClickListener { changePlaceholder(R.drawable.ic_avatar_joker) }
        menImageView.setOnClickListener { changePlaceholder(R.drawable.ic_avatar) }

        redButton.setOnClickListener { changeColorBackground(R.color.red, R.color.yellow) }
        yellowButton.setOnClickListener { changeColorBackground(R.color.yellow, R.color.red) }
        whiteButton.setOnClickListener { changeColorBackground(R.color.white, R.color.black) }
        blueButton.setOnClickListener { changeColorBackground(R.color.blue, R.color.grey) }
        blackButton.setOnClickListener { changeColorBackground(R.color.black, R.color.white) }
        greyButton.setOnClickListener { changeColorBackground(R.color.grey, R.color.white) }
    }

    private fun changeColorBackground(background: Int, colorText: Int) = with(binding) {
        profilePlaceholderImageView.colorImage(background, contextBinding)
        placeholderTextView.colorText(colorText, contextBinding)
    }

    private fun changePlaceholder(placeholder: Int) = with(binding) {
        profileImageView.glidePlaceholder(placeholder, contextBinding, onAccept = {}, onError = {})
    }

    private fun observer() = with(binding) {
        viewModel.uiStateProfile.observe(viewLifecycleOwner) { state ->
            when(state) {
                is AppUiState.Loading -> {
                    println("Loading...")
                }

                is AppUiState.Error -> {
                    Snackbar.make(binding.root, state.error, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    profileImageView.glidePlaceholder(
                        R.drawable.ic_avatar,
                        contextBinding,
                        onAccept = {},
                        onError = {})
                }

                is AppUiState.Success -> {
                    nameTextView.text = state.data.name
                    validateImageProfile(state.data)
                }

                else -> {}
            }
        }
    }

    private fun validateImageProfile(data: ProfileModel) = with(binding) {
        profileImageView.validateImageProfile(
            data,
            placeholderTextLayout,
            placeholderTextView,
            colorContainer,
            placeholderContainer,
            contextBinding
        )
    }
}
