package com.example.recyclerviewdiscussion.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.recyclerviewdiscussion.databinding.DialogItemBinding
import com.example.recyclerviewdiscussion.models.ShoppingItemModels
import kotlin.random.Random

class ItemDialogFragment (
    private val item: ShoppingItemModels?,
    private val onSave: (ShoppingItemModels) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogItemBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogItemBinding.inflate(layoutInflater)

        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setTitle(if (item == null) "Add Item" else "Edit Item")
            .setPositiveButton("Save") { _, _ ->
                val name = binding.etName.text.toString()
                val qty = binding.etQuantity.text.toString().toIntOrNull() ?: 1
                val newItem = item?.copy(name = name, quantity = qty) ?: ShoppingItemModels(
                    id = Random.nextInt(),
                    name = name,
                    quantity = qty
                )
                onSave(newItem)
            }
            .setNegativeButton("Cancel", null)

        // Pre-fill if editing
        item?.let {
            binding.etName.setText(it.name)
            binding.etQuantity.setText(it.quantity.toString())
        }

        return builder.create()
    }
}