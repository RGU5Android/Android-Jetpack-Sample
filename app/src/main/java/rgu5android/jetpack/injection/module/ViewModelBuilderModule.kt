package rgu5android.jetpack.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import rgu5android.jetpack.helper.AppViewModelProviderFactory
import rgu5android.jetpack.injection.annotation.ViewModelKey
import rgu5android.jetpack.ui.follow.FollowViewModel
import rgu5android.jetpack.ui.repository.RepositoryViewModel
import rgu5android.jetpack.ui.user.GithubUserViewModel

@Module
abstract class ViewModelBuilderModule {

	@Binds
	@IntoMap
	@ViewModelKey(GithubUserViewModel::class)
	abstract fun bindGithubUserViewModel(viewModel: GithubUserViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(FollowViewModel::class)
	abstract fun bindFollowViewModel(viewModel: FollowViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(RepositoryViewModel::class)
	abstract fun bindRepositoryViewModel(viewModel: RepositoryViewModel): ViewModel

	@Binds
	abstract fun bindViewModelFactory(viewModelFactory: AppViewModelProviderFactory): ViewModelProvider.Factory
}