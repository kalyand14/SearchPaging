package com.poc.pagingwithsearch.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.R
import com.poc.pagingwithsearch.domain.Genre

class GenreAdapter(
    var arrayList: List<Genre>,
    private val onclick: (Int) -> Unit
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    var selectedPosition = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Initialize view
        val view: View = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_main, parent,
                false
            )
        // Pass holder view
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        // Set text on radio button
        holder.radioButton.setText(arrayList[position].name)

        // Checked selected radio button
        holder.radioButton.isChecked = position == selectedPosition

        holder.radioButton.setOnClickListener {
            if (position == selectedPosition) {
                holder.radioButton.isChecked = false
                selectedPosition = -1
            } else {
                selectedPosition = position
                notifyDataSetChanged()
            }
        }
    }

    fun getSelectedItem(): Int = arrayList[selectedPosition].id

    override fun getItemId(position: Int): Long {
        // pass position
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        // pass position
        return position
    }

    override fun getItemCount(): Int {
        // pass total list size
        return arrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize variable
        var radioButton: RadioButton

        init {

            // Assign variable
            radioButton = itemView.findViewById(R.id.radio_button)
        }
    }
}
