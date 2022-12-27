package com.poc.pagingwithsearch.presentation.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.poc.pagingwithsearch.R
import com.poc.pagingwithsearch.databinding.AdapterMovieBinding
import com.poc.pagingwithsearch.domain.Movie

class MovieViewHolder(private val binding: AdapterMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.name.text = movie.original_title
        binding.desc.text = movie.overview
        binding.date.text = movie.release_date
        binding.genre.text = movie.genre_ids.toString()
       /* Glide.with(binding.root)
            .load("https://image.tmdb.org/t/p/w300" + movie.poster_path)
            .placeholder(R.drawable.ic_baseline_broken_image_24)
            .into(binding.imageview)*/
    }
}