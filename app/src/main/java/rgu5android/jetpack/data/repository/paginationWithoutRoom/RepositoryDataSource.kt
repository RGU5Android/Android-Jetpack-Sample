package rgu5android.jetpack.data.repository.paginationWithoutRoom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import retrofit2.Call
import retrofit2.Response
import rgu5android.jetpack.data.ApiService
import rgu5android.jetpack.data.model.RepositorySearchResponse
import rgu5android.jetpack.helper.ApiStatus
import rgu5android.jetpack.vo.Repository

class RepositoryDataSource(
	private val apiService: ApiService,
	private val query: String
) :
		PageKeyedDataSource<Int, Repository>() {

	private val perPage = 100
	private val firstPage = 1

	private val _apiStatus = MutableLiveData<ApiStatus>()

	val apiStatus: LiveData<ApiStatus>
		get() = _apiStatus

	override fun loadInitial(
		params: LoadInitialParams<Int>,
		callback: LoadInitialCallback<Int, Repository>
	) {
		_apiStatus.postValue(ApiStatus.LOADING)

		apiService.searchRepository(query = query, perPage = perPage, page = firstPage)
				.enqueue(object : retrofit2.Callback<RepositorySearchResponse> {

					override fun onFailure(
						call: Call<RepositorySearchResponse>,
						t: Throwable
					) {
						_apiStatus.postValue(
								ApiStatus.ERROR.copy(msg = t.message ?: "Unknown Error!!!")
						)
					}

					override fun onResponse(
						call: Call<RepositorySearchResponse>,
						response: Response<RepositorySearchResponse>
					) {
						if (response.isSuccessful) {
							_apiStatus.postValue(ApiStatus.SUCCESS)
							callback.onResult(response.body()?.items!!, null, firstPage + 1)
						} else {
							_apiStatus.postValue(
									ApiStatus.ERROR.copy(
											msg = response.errorBody()?.string()
													?: "Unknown Error!!!"
									)
							)
						}
					}
				})
	}

	override fun loadAfter(
		params: LoadParams<Int>,
		callback: LoadCallback<Int, Repository>
	) {
		_apiStatus.postValue(ApiStatus.LOADING)
		apiService.searchRepository(query = query, perPage = perPage, page = params.key)
				.enqueue(object : retrofit2.Callback<RepositorySearchResponse> {
					override fun onFailure(
						call: Call<RepositorySearchResponse>,
						t: Throwable
					) {
						_apiStatus.postValue(
								ApiStatus.ERROR.copy(msg = t.message ?: "Unknown Error!!!")
						)
					}

					override fun onResponse(
						call: Call<RepositorySearchResponse>,
						response: Response<RepositorySearchResponse>
					) {
						if (response.isSuccessful) {
							_apiStatus.postValue(ApiStatus.SUCCESS)
							val adjacentPageKey =
								if (params.key * perPage >= response.body()?.total!!) {
									null
								} else {
									params.key + 1
								}
							callback.onResult(response.body()?.items!!, adjacentPageKey)
						} else {
							_apiStatus.postValue(
									ApiStatus.ERROR.copy(
											msg = response.errorBody()?.string()
													?: "Unknown Error!!!"
									)
							)
						}
					}
				})
	}

	override fun loadBefore(
		params: LoadParams<Int>,
		callback: LoadCallback<Int, Repository>
	) {
		// Ignored for current usecase
		// Please refer: https://www.simplifiedcoding.net/android-paging-library-tutorial/
	}
}