package rgu5android.jetpack.injection.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import rgu5android.jetpack.data.ApiService
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class ApiServiceModule {

	@Provides
	@Singleton
	fun provideRetrofitApiService(retrofit: Retrofit): ApiService {
		return retrofit.create(ApiService::class.java)
	}
}