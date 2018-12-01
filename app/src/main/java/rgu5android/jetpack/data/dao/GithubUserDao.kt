package rgu5android.jetpack.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rgu5android.jetpack.vo.GithubUser

@Dao
interface GithubUserDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(vararg users: GithubUser)

	@Query(value = "SELECT * FROM USER WHERE login = :user")
	fun findUser(user: String): LiveData<GithubUser>
}