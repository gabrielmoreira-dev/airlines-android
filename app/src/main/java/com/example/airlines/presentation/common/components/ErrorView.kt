package com.example.airlines.presentation.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.airlines.R
import com.example.airlines.databinding.ResErrorViewBinding

class ErrorView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {
    private val binding = ResErrorViewBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        binding.root.setOnClickListener {}
        context.obtainStyledAttributes(attrs, R.styleable.StatefulView).apply {
            getString(R.styleable.StatefulView_description)?.let {
                setDescription(it)
            }
            setVisibility(getBoolean(R.styleable.StatefulView_visible, false))
            recycle()
        }
    }

    fun setDescription(description: String) {
        binding.descriptionView.text = description
    }

    fun setOnTryAgainListener(listener: View.OnClickListener) {
        binding.tryAgainButton.setOnClickListener(listener)
    }

    fun show() {
        setVisibility(true)
    }

    fun dismiss() {
        setVisibility(false)
    }

    private fun setVisibility(isEnabled: Boolean) {
        binding.root.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }
}