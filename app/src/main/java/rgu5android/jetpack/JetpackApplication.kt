package rgu5android.jetpack

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import rgu5android.jetpack.injection.component.DaggerAppComponent

class JetpackApplication : DaggerApplication() {

	override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
		return DaggerAppComponent.builder()
				.create(this)
				.build()
	}
}
