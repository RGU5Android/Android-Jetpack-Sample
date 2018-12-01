package rgu5android.jetpack.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import rgu5android.jetpack.JetpackApplication
import rgu5android.jetpack.injection.module.ActivityBuilderModule
import rgu5android.jetpack.injection.module.ApiServiceModule
import rgu5android.jetpack.injection.module.DatabaseModule
import rgu5android.jetpack.injection.module.FragmentBuilderModule
import rgu5android.jetpack.injection.module.NetworkModule
import rgu5android.jetpack.injection.module.ViewModelBuilderModule
import javax.inject.Singleton

@Singleton
@Component(
		modules = [
			ActivityBuilderModule::class,
			AndroidSupportInjectionModule::class,
			ApiServiceModule::class,
			DatabaseModule::class,
			FragmentBuilderModule::class,
			NetworkModule::class,
			ViewModelBuilderModule::class
		]
)
interface AppComponent : AndroidInjector<JetpackApplication> {
	@Component.Builder
	interface Builder {

		@BindsInstance
		fun create(application: Application): Builder

		fun build(): AppComponent
	}
}