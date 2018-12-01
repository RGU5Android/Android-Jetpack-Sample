package rgu5android.jetpack.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import rgu5android.jetpack.data.repository.GithubUserRepository
import rgu5android.jetpack.helper.ApiStatus
import rgu5android.jetpack.helper.PaginatedResult
import rgu5android.jetpack.vo.Repository
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(
	private val githubUserRepository: GithubUserRepository
) : ViewModel() {
	val query = MutableLiveData<String>()

	var isNetworkOnly = false

	private val queryResult: LiveData<PaginatedResult<Repository>> = Transformations.map(query) {
		if (isNetworkOnly) {
			githubUserRepository.searchRepository(it)
		} else {
			githubUserRepository.searchRepositoryRoom(it)
		}
	}

	val data: LiveData<PagedList<Repository>> = Transformations.switchMap(queryResult) {
		it.data
	}

	val apiStatus: LiveData<ApiStatus> = Transformations.switchMap(queryResult) {
		it.apiStatus
	}
}
