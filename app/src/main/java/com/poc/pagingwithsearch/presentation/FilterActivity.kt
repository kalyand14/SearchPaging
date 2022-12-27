package com.poc.pagingwithsearch.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.poc.pagingwithsearch.databinding.ActivityFilterBinding
import com.poc.pagingwithsearch.di.Injection
import com.poc.pagingwithsearch.presentation.adapter.GenreAdapter
import kotlinx.coroutines.launch

class FilterActivity : AppCompatActivity() {

    lateinit var viewModel: GenreViewModel

    lateinit var binding: ActivityFilterBinding

    var selectedItem: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(owner = this)
        )[GenreViewModel::class.java]


        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.state
                .collect {
                    binding.recyclerView.adapter = GenreAdapter(it) { selectedId ->
                        selectedItem = selectedId
                    }
                }
        }

        viewModel.getGenre()

        binding.selectButton.setOnClickListener {
            val id = (binding.recyclerView.adapter as GenreAdapter).getSelectedItem()
            setResult(RESULT_OK, Intent().putExtra("selectedId", id.toString()))
            finish()
        }
    }
}