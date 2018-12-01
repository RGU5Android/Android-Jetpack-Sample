package rgu5android.jetpack.helper

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class PaginatedResult<T>(
	val data: LiveData<PagedList<T>>,
	val apiStatus: LiveData<ApiStatus>
)