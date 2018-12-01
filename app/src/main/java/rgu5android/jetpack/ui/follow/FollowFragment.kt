package rgu5android.jetpack.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import rgu5android.jetpack.NavigationActivity
import rgu5android.jetpack.R
import rgu5android.jetpack.databinding.FollowFragmentBinding
import rgu5android.jetpack.extensions.showSnack
import rgu5android.jetpack.helper.ApiEmptyResponse
import rgu5android.jetpack.helper.ApiErrorResponse
import rgu5android.jetpack.helper.ApiSuccessResponse
import javax.inject.Inject

class FollowFragment : DaggerFragment() {

	@Inject
	internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

	private lateinit var viewModel: FollowViewModel
	private lateinit var binding: FollowFragmentBinding
	private val followAdapter: FollowAdapter =
		FollowAdapter()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.follow_fragment, container, false)
		binding.setLifecycleOwner(this)
		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
				.get(FollowViewModel::class.java)

		setUpRecycleView()
		setUpSubscriber()

		viewModel.input.postValue(
				FollowViewModel.Input(
						FollowFragmentArgs.fromBundle(arguments).username,
						FollowFragmentArgs.fromBundle(arguments).type
				)
		)

		if (FollowFragmentArgs.fromBundle(arguments).type == 0) {
			(activity as NavigationActivity).supportActionBar?.title = "Followers"
		} else {
			(activity as NavigationActivity).supportActionBar?.title = "Following"
		}
	}

	private fun setUpRecycleView() {
		binding.recyclerView.apply {
			setHasFixedSize(true)
			adapter = followAdapter
		}
	}

	private fun setUpSubscriber() {
		viewModel.data.observe(this, Observer {
			binding.recyclerView.visibility = View.VISIBLE
			binding.progressBar.visibility = View.GONE
			when (it) {
				is ApiSuccessResponse -> {
					followAdapter.submitList(it.body)
				}
				is ApiEmptyResponse -> {
					followAdapter.submitList(null)
				}
				is ApiErrorResponse -> {
					binding.root.showSnack(it.errorMessage)
				}
			}
		})
	}

}
