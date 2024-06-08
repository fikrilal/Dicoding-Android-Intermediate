package com.fikrilal.narate_mobile_apps.homepage.utils

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil

class DifferWrapperImpl<T : Any>(
    private val differ: AsyncPagingDataDiffer<T>
) : DifferWrapper<T> {
    override suspend fun submitData(pagingData: PagingData<T>) {
        differ.submitData(pagingData)
    }
}