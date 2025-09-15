package com.example.recyclerviewdiscussion

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewdiscussion.adapters.ShoppingListAdapter
import com.example.recyclerviewdiscussion.databinding.ActivityMainBinding
import com.example.recyclerviewdiscussion.fragments.ItemDialogFragment
import com.example.recyclerviewdiscussion.models.ShoppingItemModels

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ShoppingListAdapter
    private val items = mutableListOf<ShoppingItemModels>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ShoppingListAdapter(items)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener {
            showItemDialog(null)
            toggleEmptyView()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter(query ?: "")
                toggleEmptyView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText ?: "")
                toggleEmptyView()
                return true
            }
        })

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        toggleEmptyView()
    }

    private fun showItemDialog(item: ShoppingItemModels?) {
        val dialog = ItemDialogFragment(item) { newItem ->
            if (item == null) {
                // Add new
                adapter.addItem(newItem)
                adapter.notifyItemInserted(items.size - 1)
                toggleEmptyView()
            } else {
                // Update existing
                val index = items.indexOfFirst { it.id == newItem.id }
                if (index != -1) {
                    items[index] = newItem
                    adapter.notifyItemChanged(index)
                    toggleEmptyView()
                }
            }
        }
        dialog.show(supportFragmentManager, "ItemDialog")
    }

    private fun toggleEmptyView() {
        if (adapter.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
        } else {
            binding.tvEmpty.visibility = View.GONE
        }
    }
}