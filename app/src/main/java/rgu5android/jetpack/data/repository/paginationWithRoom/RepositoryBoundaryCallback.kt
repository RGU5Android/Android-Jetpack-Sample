package rgu5android.jetpack.data.repository.paginationWithRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rgu5android.jetpack.data.ApiService
import rgu5android.jetpack.data.dao.RepositoryDao
import rgu5android.jetpack.data.model.RepositorySearchResponse
import rgu5android.jetpack.helper.ApiStatus
import rgu5android.jetpack.helper.AppExecutors
import rgu5android.jetpack.vo.Repository

class RepositoryBoundaryCallback(
	private val query: String,
	private val apiService: ApiService,
	private val repositoryDao: RepositoryDao,
	private val appExecutors: AppExecutors
) : PagedList.BoundaryCallback<Repository>() {

	companion object {
		const val DATABASE_PAGE_SIZE = 10
		const val NETWORK_PAGE_SIZE = 2 * DATABASE_PAGE_SIZE
	}

	private var lastRequestedPage = 1

	private val _apiStatus = MutableLiveData<ApiStatus>()

	val apiStatus: LiveData<ApiStatus>
		get() = _apiStatus

	private var isRequestInProgress = false

	override fun onZeroItemsLoaded() {
		super.onZeroItemsLoaded()
		_apiStatus.postValue(ApiStatus.LOADING)
		requestAndSaveData(query)
	}

	override fun onItemAtEndLoaded(itemAtEnd: Repository) {
		super.onItemAtEndLoaded(itemAtEnd)
		requestAndSaveData(query)
	}

	private fun requestAndSaveData(query: String) {
		if (isRequestInProgress) return

		_apiStatus.postValue(ApiStatus.LOADING)

		apiService.searchRepository(
				query = query,
				page = lastRequestedPage,
				perPage = NETWORK_PAGE_SIZE
		)
				.enqueue(object : Callback<RepositorySearchResponse> {
					override fun onFailure(
						call: Call<RepositorySearchResponse>,
						t: Throwable
					) {
						isRequestInProgress = false
						_apiStatus.postValue(
								ApiStatus.ERROR.copy(msg = t.message ?: "Unknown Error!!!")
						)
					}

					override fun onResponse(
						call: Call<RepositorySearchResponse>,
						response: Response<RepositorySearchResponse>
					) {
						if (response.isSuccessful) {
							appExecutors.diskIO()
									.execute {
										response.body()
												?.items?.let {
											repositoryDao.insert(it)
										}
										lastRequestedPage++
										isRequestInProgress = false
										_apiStatus.postValue(ApiStatus.SUCCESS)
									}
						} else {
							_apiStatus.postValue(
									ApiStatus.ERROR.copy(
											msg = response.errorBody()?.string()
													?: "Unknown Error!!!"
									)
							)
							isRequestInProgress = false
						}
					}
				})
	}
}