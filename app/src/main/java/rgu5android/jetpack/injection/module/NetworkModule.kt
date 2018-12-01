package rgu5android.jetpack.injection.module

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rgu5android.jetpack.data.ApiService
import rgu5android.jetpack.helper.LiveDataCallAdapterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

	@Provides
	@Singleton
	fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
		val interceptor = HttpLoggingInterceptor(
				HttpLoggingInterceptor.Logger { message ->
					Log.i(
							"HttpLoggingInterceptor", message
					)
				})
		interceptor.level = HttpLoggingInterceptor.Level.BASIC
		return interceptor
	}

	@Provides
	@Singleton
	fun provideCache(cacheFile: File): Cache {
		return Cache(cacheFile, 10 * 1000 * 1000)
	}

	@Provides
	@Singleton
	fun provideCacheFile(application: Application): File {
		return File(application.cacheDir, "OkHttpCache")
	}

	@Provides
	@Singleton
	fun provideOkHttpClient(
		httpLoggingInterceptor: HttpLoggingInterceptor,
		cache: Cache
	): OkHttpClient {
		return OkHttpClient
				.Builder()
				.addInterceptor(httpLoggingInterceptor)
				.cache(cache)
				.connectTimeout(2, TimeUnit.MINUTES)
				.readTimeout(2, TimeUnit.MINUTES)
				.build()
	}

	@Provides
	@Singleton
	fun provideGson(): Gson {
		return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
				.create()
	}

	@Provides
	@Singleton
	fun provideRetrofit(
		okHttpClient: OkHttpClient,
		gson: Gson
	): Retrofit {
		return Retrofit.Builder()
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(LiveDataCallAdapterFactory())
				.client(okHttpClient)
				.baseUrl(ApiService.baseUrl)
				.build()
	}
}