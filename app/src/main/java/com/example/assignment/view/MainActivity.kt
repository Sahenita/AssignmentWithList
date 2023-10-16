package com.example.assignment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.PicsumImage
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.viewmodel.MyViewModel
import com.example.assignment.viewmodel.MyViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = MyViewModelFactory()
        viewModel = ViewModelProvider(this,factory).get(MyViewModel::class.java)
        binding.rvAllChallenge.layoutManager = LinearLayoutManager(this)
        binding.swipeLayout.setOnRefreshListener {
            // Handle the refresh action
            binding.swipeLayout.isRefreshing = false
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    binding.swipeLayout.isRefreshing = false
                    viewModel.refresh()
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

        }
        val adapter = MyAdapter(this)

        // Observe the paging data

        viewModel.pagingData.observe(this) { pagingData ->
            adapter.submitData(this.lifecycle, pagingData)
        }
        binding.rvAllChallenge.adapter = adapter

    }


}