package rgu5android.jetpack.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import rgu5android.jetpack.data.ApiService
import rgu5android.jetpack.data.dao.GithubUserDao
import rgu5android.jetpack.data.dao.RepositoryDao
import rgu5android.jetpack.data.repository.paginationWithRoom.RepositoryBoundaryCallback
import rgu5android.jetpack.data.repository.paginationWithRoom.RepositoryBoundaryCallback.Companion.DATABASE_PAGE_SIZE
import rgu5android.jetpack.data.repository.paginationWithoutRoom.RepositoryDataSourceFactory
import rgu5android.jetpack.helper.ApiResponse
import rgu5android.jetpack.helper.ApiStatus
import rgu5android.jetpack.helper.AppExecutors
import rgu5android.jetpack.helper.NetworkBoundResource
import rgu5android.jetpack.helper.PaginatedResult
import rgu5android.jetpack.helper.Resource
import rgu5android.jetpack.vo.Follow
import rgu5android.jetpack.vo.GithubUser
import rgu5android.jetpack.vo.Repository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
	private val appExecutors: AppExecutors,
	private val githubUserDao: GithubUserDao,
	private val repositoryDao: RepositoryDao,
	private val apiService: ApiService
) {
	// Normal API call where response is saved into Room Database
	fun findUser(user: String): LiveData<Resource<GithubUser>> {
		return object : NetworkBoundResource<GithubUser, GithubUser>(appExecutors) {
			override fun saveCallResult(item: GithubUser) = githubUserDao.insert(item)

			override fun shouldFetch(data: GithubUser?): Boolean = true

			override fun loadFromDb(): LiveData<GithubUser> = githubUserDao.findUser(user)

			override fun createCall(): LiveData<ApiResponse<GithubUser>> =
				apiService.findUser(userName = user)

		}.asLiveData()
	}

	// Normal API call where response is not saved into Room Database
	fun getFollowers(user: String): LiveData<ApiResponse<List<Follow>>> {
		return apiService.userFollowers(userName = user)
	}

	// Normal API call where response is not saved into Room Database
	fun getFollowings(user: String): LiveData<ApiResponse<List<Follow>>> {
		return apiService.userFollowings(userName = user)
	}

	fun searchRepositoryRoom(query: String): PaginatedResult<Repository> {

		val dataSourceFactory = repositoryDao.searchRepository(query)

		val boundaryCallback = RepositoryBoundaryCallback(
				query = query,
				apiService = apiService,
				repositoryDao = repositoryDao,
				appExecutors = appExecutors
		)

		val apiStatus: LiveData<ApiStatus> = boundaryCallback.apiStatus

		val data: LiveData<PagedList<Repository>> =
			LivePagedListBuilder(dataSourceFactory, DATABASE_PAGE_SIZE).setBoundaryCallback(
					boundaryCallback
			)
					.build()

		return PaginatedResult(
				data = data,
				apiStatus = apiStatus
		)
	}

	fun searchRepository(query: String): PaginatedResult<Repository> {
		val repositoryDataSourceFactory =
			RepositoryDataSourceFactory(apiService, query)

		val pagedListConfig = PagedList.Config.Builder()
				.setEnablePlaceholders(false)
				.setPageSize(20)
				.build()

		return PaginatedResult(
				data = LivePagedListBuilder(repositoryDataSourceFactory, pagedListConfig).build(),
				apiStatus = Transformations.switchMap(
						repositoryDataSourceFactory.repositoryLiveDataSource
				) {
					it.apiStatus
				}
		)

	}
}