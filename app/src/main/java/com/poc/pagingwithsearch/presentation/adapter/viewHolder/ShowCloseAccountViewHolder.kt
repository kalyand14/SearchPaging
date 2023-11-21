package com.poc.pagingwithsearch.presentation.adapter.viewHolder

import android.provider.ContactsContract.Data
import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.databinding.ItemCloseaccountBinding
import com.poc.pagingwithsearch.domain.DataItem

class ShowCloseAccountViewHolder(private val binding: ItemCloseaccountBinding, private val
onToggle: (DataItem) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: DataItem.ShowCloseAccount) {
        binding.toggleSwitch.isChecked = data.status
        binding.toggleSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Handle the toggle event
            if (isChecked) {
                // The switch is ON
                // Perform actions when the element is toggled ON
                data.status = true
                onToggle(data)
            } else {
                // The switch is OFF
                // Perform actions when the element is toggled OFF
                data.status = false
                onToggle(data)
            }
        }
    }
}
