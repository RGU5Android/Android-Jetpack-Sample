package rgu5android.jetpack.data

import androidx.room.Database
import androidx.room.RoomDatabase
import rgu5android.jetpack.data.dao.GithubUserDao
import rgu5android.jetpack.data.dao.RepositoryDao
import rgu5android.jetpack.vo.GithubUser
import rgu5android.jetpack.vo.Repository

@Database(
		entities = [
			(GithubUser::class),
			(Repository::class)
		],
		version = 1,
		exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun githubUserDao(): GithubUserDao

	abstract fun repositoryDao(): RepositoryDao
}