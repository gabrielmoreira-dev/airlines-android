package com.example.airlines.presentation.common.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.example.airlines.R
import com.example.airlines.databinding.ResLoadingViewBinding

class LoadingView(context: Context, attrs: AttributeSet): LinearLayout(context, attrs) {
    private val binding = ResLoadingViewBinding
        .inflate(LayoutInflater.from(context), this, true)

    init {
        binding.root.setOnClickListener {}
        context.obtainStyledAttributes(attrs, R.styleable.LoadingView).apply {
            val isEnabled = getBoolean(R.styleable.LoadingView_visible, false)
            setVisibility(isEnabled)
            recycle()
        }
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