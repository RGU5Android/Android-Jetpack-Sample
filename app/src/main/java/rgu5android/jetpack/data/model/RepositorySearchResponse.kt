package rgu5android.jetpack.data.model

import com.google.gson.annotations.SerializedName
import rgu5android.jetpack.vo.Repository

data class RepositorySearchResponse(
	@field:SerializedName("total_count") val total: Int = 0,
	@field:SerializedName("items") val items: List<Repository> = emptyList()
)
