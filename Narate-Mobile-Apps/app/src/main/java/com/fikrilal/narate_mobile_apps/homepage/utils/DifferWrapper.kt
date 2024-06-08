package com.fikrilal.narate_mobile_apps.homepage.utils

import androidx.paging.PagingData

interface DifferWrapper<T : Any> {
    suspend fun submitData(pagingData: PagingData<T>)
}