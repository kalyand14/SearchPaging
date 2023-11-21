package com.poc.pagingwithsearch.presentation.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.databinding.ItemSubaccountBinding
import com.poc.pagingwithsearch.domain.DataItem

class SubAccountViewHolder(private val binding: ItemSubaccountBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(subAccount: DataItem.SubAccount) {
        binding.description.text = subAccount.description
    }

}
