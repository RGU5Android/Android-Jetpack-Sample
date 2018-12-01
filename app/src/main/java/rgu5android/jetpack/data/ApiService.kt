package rgu5android.jetpack.data

import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rgu5android.jetpack.data.model.RepositorySearchResponse
import rgu5android.jetpack.helper.ApiResponse
import rgu5android.jetpack.vo.Follow
import rgu5android.jetpack.vo.GithubUser

interface ApiService {
	companion object {
		const val baseUrl: String = "https://api.github.com/"
		const val clientId: String = "69c8b21e8eb038ff111a"
		const val clientSecret: String = "1fd36a0a5a0dfdc58f835092da358ab9efceb079"
	}

	@GET("users/{userName}")
	fun findUser(
		@Path("userName") userName: String,
		@Query("client_id") clientId: String = Companion.clientId,
		@Query("client_secret") clientSecret: String = Companion.clientSecret
	): LiveData<ApiResponse<GithubUser>>

	@GET("users/{userName}/followers")
	fun userFollowers(
		@Path("userName") userName: String,
		@Query("client_id") clientId: String = Companion.clientId,
		@Query("client_secret") clientSecret: String = Companion.clientSecret
	): LiveData<ApiResponse<List<Follow>>>

	@GET("users/{userName}/following")
	fun userFollowings(
		@Path("userName") userName: String,
		@Query("client_id") clientId: String = Companion.clientId,
		@Query("client_secret") clientSecret: String = Companion.clientSecret
	): LiveData<ApiResponse<List<Follow>>>

	@GET("search/repositories")
	fun searchRepository(
		@Query("client_id") clientId: String = Companion.clientId,
		@Query("client_secret") clientSecret: String = Companion.clientSecret,
		@Query("sort") sortBy: String = "stars",
		@Query("order") order: String = "desc",
		@Query("q") query: String,
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	): Call<RepositorySearchResponse>

	@GET("users/rgu5android")
	fun authenticate(
		@Query("client_id") clientId: String = Companion.clientId,
		@Query("client_secret") clientSecret: String = Companion.clientSecret
	): Call<JsonObject>
}