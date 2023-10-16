package com.example.assignment.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.assignment.PicsumImage
import com.example.assignment.repository.MyRepository

class ImagePagingSource : PagingSource<Int, PicsumImage>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PicsumImage> {
        try {
            val page = params.key ?: 1
            val response =MyRepository().GetDataList(page)// Fetch data from your data source (e.g., API)
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1
            return LoadResult.Page(data = response, prevKey = prevKey, nextKey = nextKey)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PicsumImage>): Int? {
       //TODO("Not yet implemented")
        return 0;
    }
}