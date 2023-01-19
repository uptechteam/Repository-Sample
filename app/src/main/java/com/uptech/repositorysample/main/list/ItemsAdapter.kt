package com.uptech.repositorysample.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uptech.repositorysample.databinding.ItemBinding
import com.uptech.repositorysample.entity.Item
import com.uptech.repositorysample.main.list.ItemsAdapter.ItemViewHolder

class ItemsAdapter(
  private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

  var items: List<Item> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
    ItemViewHolder(
      ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int =
    items.size

  inner class ItemViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
      binding.root.setOnClickListener { onItemClick(items[adapterPosition].id) }
    }

    fun bind(item: Item) {
      with(binding) {
        name.text = item.name
        onStock.text = "${item.countOnStock} on stock"
        price.text = item.cost.toString()
        image.setImageResource(item.imageRes)
      }
    }
  }
}