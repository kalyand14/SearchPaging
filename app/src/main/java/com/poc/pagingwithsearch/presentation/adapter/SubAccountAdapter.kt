package com.poc.pagingwithsearch.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.R
import com.poc.pagingwithsearch.databinding.ItemCloseaccountBinding
import com.poc.pagingwithsearch.databinding.ItemSubaccountBinding
import com.poc.pagingwithsearch.domain.DataItem
import com.poc.pagingwithsearch.presentation.adapter.viewHolder.ShowCloseAccountViewHolder
import com.poc.pagingwithsearch.presentation.adapter.viewHolder.SubAccountViewHolder

class SubAccountAdapter(
    private var items: List<DataItem>,
    private val onToggle: (DataItem) -> Unit
) : RecyclerView.Adapter<RecyclerView
.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_subaccount -> {
                val binding = ItemSubaccountBinding.inflate(inflater, parent, false)
                return SubAccountViewHolder(binding)
            }
            else -> {
                val binding = ItemCloseaccountBinding.inflate(inflater, parent, false)
                return ShowCloseAccountViewHolder(binding, onToggle)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (items[position]) {
            is DataItem.SubAccount -> {
                (holder as SubAccountViewHolder).bind(items[position] as DataItem.SubAccount)
            }
            else -> {
                (holder as ShowCloseAccountViewHolder).bind(items[position] as DataItem.ShowCloseAccount)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DataItem.SubAccount -> R.layout.item_subaccount
            else -> R.layout.item_closeaccount
        }
    }

    fun updateData(updatedItemList: List<DataItem>) {
        items = updatedItemList
        notifyDataSetChanged()
    }

}