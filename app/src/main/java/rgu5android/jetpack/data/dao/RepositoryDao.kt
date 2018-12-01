package rgu5android.jetpack.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rgu5android.jetpack.vo.Repository

@Dao
interface RepositoryDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(repositories: List<Repository>)

	@Query("SELECT * FROM REPOSITORY WHERE (name LIKE :query) OR (description LIKE :query) ORDER BY stars DESC")
	fun searchRepository(query: String): DataSource.Factory<Int, Repository>
}