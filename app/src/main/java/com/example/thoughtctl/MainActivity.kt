package com.example.thoughtctl

import ImgurAdapter
import ImgurViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {


    private val viewModel: ImgurViewModel by viewModels {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    private lateinit var adapter: ImgurAdapter
    private var viewType = ImgurAdapter.VIEW_TYPE_LIST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = ImgurAdapter(emptyList(), viewType,applicationContext)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchButton: Button = findViewById(R.id.searchButton)
        val toggleButton: ToggleButton = findViewById(R.id.toggleButton)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            viewModel.searchImages(query)
        }

        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            viewType = if (isChecked) {
                ImgurAdapter.VIEW_TYPE_GRID
            } else {
                ImgurAdapter.VIEW_TYPE_LIST
            }

            updateRecyclerView()
        }

        viewModel.imgurImages.observe(this, Observer {
            adapter.setImgurImages(it)
        })
    }

    private fun updateRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = ImgurAdapter(viewModel.imgurImages.value.orEmpty(), viewType,applicationContext)
        recyclerView.adapter = adapter

        if (viewType == ImgurAdapter.VIEW_TYPE_GRID) {
            recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(this)
        }

        adapter.notifyDataSetChanged()
    }
}
