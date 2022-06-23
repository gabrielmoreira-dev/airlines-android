package com.example.airlines.presentation.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, B : ViewBinding>(
    private val bindingInflater: (
        inflater: LayoutInflater,
        parent: ViewGroup,
        attachToParent: Boolean
    ) -> B,
    private val onItemClicked: ((T) -> Unit)?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected var items: List<T> = ArrayList()

    abstract fun bindItem(context: Context, binding: B, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        bindingInflater(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseAdapter<*, *>.ViewHolder -> holder.bind(position)
        }
    }

    override fun getItemCount() = items.size

    fun setupItems(items: List<T>) {
        this.items = items
    }

    inner class ViewHolder(binding: B) : RecyclerView.ViewHolder(binding.root) {
        private val viewBinding = binding

        fun bind(position: Int) {
            bindItem(itemView.context, viewBinding, position)
            itemView.setOnClickListener {
                onItemClicked?.let { it(items[position]) }
            }
        }
    }
}