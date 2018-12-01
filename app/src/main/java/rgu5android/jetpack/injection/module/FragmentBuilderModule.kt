package rgu5android.jetpack.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import rgu5android.jetpack.ui.NavigationFragment
import rgu5android.jetpack.ui.follow.FollowFragment
import rgu5android.jetpack.ui.repository.RepositoryFragment
import rgu5android.jetpack.ui.user.GithubUserFragment

@Module
abstract class FragmentBuilderModule {
	@ContributesAndroidInjector
	abstract fun contributeGithubUserFragment(): GithubUserFragment

	@ContributesAndroidInjector
	abstract fun contributeFollowFragment(): FollowFragment

	@ContributesAndroidInjector
	abstract fun contributeRepositoryFragment(): RepositoryFragment

	@ContributesAndroidInjector
	abstract fun contributeNavigationFragment(): NavigationFragment
}