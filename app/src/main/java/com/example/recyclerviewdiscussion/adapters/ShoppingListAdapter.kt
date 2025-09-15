package com.example.recyclerviewdiscussion.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewdiscussion.databinding.ItemShoppingBinding
import com.example.recyclerviewdiscussion.models.ShoppingItemModels

class ShoppingListAdapter(
    private val items: MutableList<ShoppingItemModels>
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private val filteredItems: MutableList<ShoppingItemModels> = mutableListOf()

    init {
        // start with all items visible
        filteredItems.addAll(items)
    }

    inner class ViewHolder(val binding: ItemShoppingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemShoppingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.binding.apply {
            tvName.text = item.name
            tvQuantity.text = "Qty: ${item.quantity}"
        }
    }

    override fun getItemCount(): Int = filteredItems.size

    // 🔹 Add item
    fun addItem(item: ShoppingItemModels) {
        items.add(item)
        filter("") // refresh full list (or keep current query if you pass it in)
    }

    // 🔹 Filter items
    fun filter(query: String) {
        filteredItems.clear()
        if (query.isEmpty()) {
            filteredItems.addAll(items)
        } else {
            filteredItems.addAll(items.filter { it.name.contains(query, ignoreCase = true) })
        }
        notifyDataSetChanged()
    }

    fun isEmpty(): Boolean = items.isEmpty()
}