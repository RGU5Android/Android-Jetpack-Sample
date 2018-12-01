package rgu5android.jetpack.vo

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
		tableName = "REPOSITORY",
		primaryKeys = ["id"]
)
data class Repository(
	@NonNull @field:SerializedName("id") val id: Long,
	@field:SerializedName("name") val name: String,
	@field:SerializedName("full_name") val fullName: String,
	@field:SerializedName("description") val description: String?,
	@field:SerializedName("html_url") val url: String,
	@field:SerializedName("stargazers_count") val stars: Int,
	@field:SerializedName("forks_count") val forks: Int,
	@field:SerializedName("language") val language: String?,
	@field:SerializedName("owner") @field:Embedded(prefix = "photo_") val owner: Owner
) {
	data class Owner(@field:SerializedName("avatar_url") val avatarUrl: String)
}
