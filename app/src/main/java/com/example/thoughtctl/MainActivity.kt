package com.example.thoughtctl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ImgurViewModel
    private lateinit var adapter: ImgurAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ImgurRepository()
        viewModel = ViewModelProvider(this, ImgurViewModelFactory(repository)).get(ImgurViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = ImgurAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val searchButton: Button = findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            viewModel.searchImages(query)
        }

        viewModel.imgurImages.observe(this, Observer {
            adapter.imgurImages = it
            adapter.notifyDataSetChanged()
        })
    }
}
