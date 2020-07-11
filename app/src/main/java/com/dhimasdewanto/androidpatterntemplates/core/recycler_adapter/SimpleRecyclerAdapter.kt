package com.dhimasdewanto.androidpatterntemplates.core.recycler_adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

class SimpleRecyclerAdapter<ItemModel>(
    private val layoutItem: Int,
    private val areItemsTheSame: (oldItem: ItemModel, newItem: ItemModel) -> Boolean,
    private val areContentsTheSame: (oldItem: ItemModel, newItem: ItemModel) -> Boolean,
    private val bindItem: (item: ItemModel, itemView: View) -> Unit,
    private val interaction: Interaction<ItemModel>? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ItemModel>() {
        override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return this@SimpleRecyclerAdapter.areItemsTheSame(oldItem, newItem)
        }

        override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean {
            return this@SimpleRecyclerAdapter.areContentsTheSame(oldItem, newItem)
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layoutItem,
                parent,
                false
            ),
            bindItem,
            interaction
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder<*> -> {
                val newHolder = holder as ItemViewHolder<ItemModel>
                newHolder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ItemModel>) {
        differ.submitList(list)
    }

    class ItemViewHolder<ItemModel>
    constructor(
        itemView: View,
        private val bindItem: (item: ItemModel, itemView: View) -> Unit,
        private val interaction: Interaction<ItemModel>?
    ) : RecyclerView.ViewHolder(itemView) {
        // TODO
        fun bind(item: ItemModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            bindItem(item, itemView)
        }
    }

    interface Interaction<ItemModel> {
        fun onItemSelected(position: Int, item: ItemModel)
    }

}