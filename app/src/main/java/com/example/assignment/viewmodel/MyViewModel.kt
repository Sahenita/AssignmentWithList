package com.example.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.assignment.PicsumImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
class MyViewModel : ViewModel() {

    private val _pagingDataLiveData = MutableLiveData<PagingData<PicsumImage>>()
    val pagingData: LiveData<PagingData<PicsumImage>> get() = _pagingDataLiveData

    private val pagingSourceFactory = { ImagePagingSource() }

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                val pagingData = Pager(pagingConfig) {
                    pagingSourceFactory()
                }.flow
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _pagingDataLiveData.value = pagingData
                    }
            } catch (e: Exception) {
                // Handle any exceptions
                e.printStackTrace()
            }
        }
    }

    companion object {
        private val pagingConfig = PagingConfig(
            pageSize = 20,
            prefetchDistance = 5,
            enablePlaceholders = false
        )
    }
}

