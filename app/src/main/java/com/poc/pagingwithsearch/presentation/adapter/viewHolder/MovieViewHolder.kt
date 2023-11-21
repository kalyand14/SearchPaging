package com.poc.pagingwithsearch.presentation.adapter.viewHolder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.poc.pagingwithsearch.databinding.AdapterMovieBinding
import com.poc.pagingwithsearch.domain.DataItem
import com.poc.pagingwithsearch.domain.Movie
import com.poc.pagingwithsearch.presentation.adapter.SubAccountAdapter

class MovieViewHolder(private val binding: AdapterMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(data: Movie) {
        binding.name.text = data.original_title
        binding.desc.text = data.overview
        binding.date.text = data.release_date
        binding.genre.text = data.genre_ids.toString()
        /* Glide.with(binding.root)
             .load("https://image.tmdb.org/t/p/w300" + movie.poster_path)
             .placeholder(R.drawable.ic_baseline_broken_image_24)
             .into(binding.imageview)*/

        var subAccountList = prepareDataList(data.subAccountList)
        binding.itemCount.text = "Number of items ${subAccountList.size - 1}"

        val subAccountAdapter = SubAccountAdapter(subAccountList) {

            val updatedSubAccountList =
                prepareUpdateDataList(data.subAccountList, (it as DataItem.ShowCloseAccount))
            binding.itemCount.text = "Number of items ${updatedSubAccountList.size - 1}"

            (binding.recyclerviewSubAccount.adapter as SubAccountAdapter).updateData(
                updatedSubAccountList
            )

            if (subAccountList.size == updatedSubAccountList.size && (it as DataItem
                .ShowCloseAccount).status) {
                Toast.makeText(
                    binding.root.context, "There are no closed Account", Toast
                        .LENGTH_SHORT
                ).show()
            }

        }
        binding.recyclerviewSubAccount.adapter = subAccountAdapter


    }

    private fun prepareDataList(subAccountList: List<DataItem>): List<DataItem> {
        return subAccountList.filter {
            when (it) {
                is DataItem.SubAccount -> it.isDisplay
                else -> true
            }
        }
    }

    private fun prepareUpdateDataList(
        subAccountList: List<DataItem>,
        dataItem: DataItem.ShowCloseAccount
    ): List<DataItem> {
        return prepareDataList(subAccountList.map {
            when (it) {
                is DataItem.ShowCloseAccount -> dataItem
                is DataItem.SubAccount -> {
                    if (it.status.not()) {
                        it.isDisplay = dataItem.status
                    }
                    it
                }
            }
        })
    }
}
