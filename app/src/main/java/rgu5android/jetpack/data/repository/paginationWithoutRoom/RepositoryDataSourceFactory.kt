package rgu5android.jetpack.data.repository.paginationWithoutRoom

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import rgu5android.jetpack.data.ApiService
import rgu5android.jetpack.vo.Repository

class RepositoryDataSourceFactory(
	private val apiService: ApiService,
	private val query: String
) : DataSource.Factory<Int, Repository>() {

	val repositoryLiveDataSource = MutableLiveData<RepositoryDataSource>()

	override fun create(): DataSource<Int, Repository> {
		val repositoryDataSource =
			RepositoryDataSource(apiService, query)
		repositoryLiveDataSource.postValue(repositoryDataSource)
		return repositoryDataSource
	}
}