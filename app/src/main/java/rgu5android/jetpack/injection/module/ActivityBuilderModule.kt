package rgu5android.jetpack.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import rgu5android.jetpack.NavigationActivity

@Module
abstract class ActivityBuilderModule {
	@ContributesAndroidInjector
	abstract fun contributeNavigationActivity(): NavigationActivity
}