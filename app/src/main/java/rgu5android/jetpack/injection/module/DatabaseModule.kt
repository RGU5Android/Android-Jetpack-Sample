package rgu5android.jetpack.injection.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import rgu5android.jetpack.data.AppDatabase
import rgu5android.jetpack.data.dao.GithubUserDao
import rgu5android.jetpack.data.dao.RepositoryDao
import javax.inject.Singleton

@Module
class DatabaseModule {

	@Singleton
	@Provides
	fun provideDatabase(application: Application): AppDatabase =
		Room.databaseBuilder(application, AppDatabase::class.java, "jetpack-database.db").build()

	@Singleton
	@Provides
	fun provideGithubUserDao(appDatabase: AppDatabase): GithubUserDao = appDatabase.githubUserDao()

	@Singleton
	@Provides
	fun provideRepositoryDao(appDatabase: AppDatabase): RepositoryDao = appDatabase.repositoryDao()
}