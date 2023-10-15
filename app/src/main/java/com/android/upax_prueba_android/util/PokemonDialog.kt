package com.android.upax_prueba_android.util

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.android.upax_prueba_android.R
import com.android.upax_prueba_android.data.model.Pokemon
import com.android.upax_prueba_android.data.model.ProfileModel
import com.android.upax_prueba_android.databinding.PokemonDialogBinding
import com.android.upax_prueba_android.util.extension.show
import com.android.upax_prueba_android.util.extension.toCapitalLetter
import com.android.upax_prueba_android.util.extension.validateImageProfile

class PokemonDialog(context: Context) : Dialog(context) {

    private lateinit var binding: PokemonDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context.setTheme(R.style.social_dialog_theme)
        initView()
        setContentView(binding.root)
        setCancelable(true)
    }

    private fun initView() {
        window?.let {

            it.setBackgroundDrawableResource(android.R.color.transparent)
            it.attributes?.gravity = Gravity.CENTER
        }

        binding = PokemonDialogBinding.inflate(layoutInflater)
        binding.socialNetworkContainerLayout.setOnClickListener {
            dismiss()
        }
    }

    private fun setupAnimation() {
        val animSlideUp: Animation =
            AnimationUtils.loadAnimation(binding.root.context, R.anim.slide_up)
        binding.containerCardView.startAnimation(animSlideUp)
    }

    fun showDialog(
        url: String,
        data: Pokemon,
        onAccept: () -> Unit,
    ) {
        if (!isShowing) {
            super.show()
        }
        setupImageProfile(data, url)
        binding.nameTextView.text = data.name.toCapitalLetter()
        binding.postImageView.show()
        binding.heightTextView.text = data.height.toString()
        binding.weightTextView.text = data.weight.toString()

        binding.nextButton.setOnClickListener {
            dismiss()
            onAccept()
        }
    }

    private fun setupImageProfile(data: Pokemon, url: String) {
        val pokemon = ProfileModel(data.id, data.name, url)
        binding.postImageView.validateImageProfile(
            pokemon,
            binding.placeholderTextLayout,
            binding.placeholderTextView,
            null,
            null,
            binding.root.context
        )
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    override fun dismiss() {
        if (isShowing) {
            super.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
    }
}
