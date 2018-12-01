package rgu5android.jetpack.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import dagger.android.support.DaggerFragment
import rgu5android.jetpack.NavigationActivity
import rgu5android.jetpack.R
import rgu5android.jetpack.databinding.GithubUserFragmentBinding
import rgu5android.jetpack.extensions.dismissKeyboard
import rgu5android.jetpack.extensions.showSnack
import rgu5android.jetpack.helper.Status
import javax.inject.Inject

class GithubUserFragment : DaggerFragment() {

	@Inject
	internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

	private lateinit var viewModel: GithubUserViewModel
	private lateinit var binding: GithubUserFragmentBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.github_user_fragment, container, false)
		binding.setLifecycleOwner(this)
		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
				.get(GithubUserViewModel::class.java)
		binding.vm = viewModel

		binding.buttonFollower.setOnClickListener {
			dismissKeyboard()
			if (viewModel.query.value != null && viewModel.data.value != null) {
				val directions =
					GithubUserFragmentDirections.ActionGithubUserFragmentToFollowFragment(
							viewModel.query.value!!,
							0
					)
				binding.root.findNavController()
						.navigate(directions)
			} else {
				binding.root.showSnack("Invalid Username")
			}
		}

		binding.buttonFollowing.setOnClickListener {
			dismissKeyboard()
			if (viewModel.query.value != null && viewModel.data.value != null) {
				val directions =
					GithubUserFragmentDirections.ActionGithubUserFragmentToFollowFragment(
							viewModel.query.value!!,
							1
					)
				binding.root.findNavController()
						.navigate(directions)
			} else {
				binding.root.showSnack("Invalid Username")
			}
		}

		setupSubscribers()
	}

	private fun setupSubscribers() {
		viewModel.response.observe(this, Observer { it ->
			it?.let { data ->
				when (data.apiStatus.status) {
					Status.LOADING -> {
						viewModel.data.postValue("")
						binding.progressBar.visibility = View.VISIBLE
					}
					Status.SUCCESS -> {
						viewModel.data.postValue(data.data.toString())
						viewModel.imageUrl.postValue(data.data?.avatarUrl)
						binding.progressBar.visibility = View.GONE
					}
					Status.ERROR -> {
						binding.root.showSnack(data.apiStatus.msg)
						binding.progressBar.visibility = View.GONE
					}
				}
			}
		})

		viewModel.query.observe(this, Observer { it ->
			it?.let { userName ->
				(activity as NavigationActivity).title = userName
			}
		})
	}

	override fun onCreateOptionsMenu(
		menu: Menu?,
		inflater: MenuInflater?
	) {
		super.onCreateOptionsMenu(menu, inflater)
		menu?.clear()

		inflater?.inflate(R.menu.menu_user_fragment, menu)
		val searchView = SearchView(
				(context as NavigationActivity).supportActionBar?.themedContext
						?: context
		)
		menu?.findItem(R.id.action_search)
				.apply {
					this?.let {
						setShowAsAction(
								MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM
						)
						actionView = searchView
					}
				}

		searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String): Boolean {
				viewModel.query.postValue(query)
				dismissKeyboard()
				return false
			}

			override fun onQueryTextChange(newText: String): Boolean {
				return false
			}
		})
	}
}
