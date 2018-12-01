package rgu5android.jetpack.ui.repository

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
import dagger.android.support.DaggerFragment
import rgu5android.jetpack.NavigationActivity
import rgu5android.jetpack.R
import rgu5android.jetpack.databinding.RepositoryFragmentBinding
import rgu5android.jetpack.extensions.dismissKeyboard
import rgu5android.jetpack.extensions.showSnack
import rgu5android.jetpack.helper.Status
import javax.inject.Inject

class RepositoryFragment : DaggerFragment() {

	@Inject
	lateinit var viewModelProviderFactory: ViewModelProvider.Factory

	private lateinit var viewModel: RepositoryViewModel
	private lateinit var binding: RepositoryFragmentBinding

	private lateinit var repositoryAdapter: RepositoryAdapter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = DataBindingUtil.inflate(inflater, R.layout.repository_fragment, container, false)
		binding.setLifecycleOwner(this)
		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
				.get(RepositoryViewModel::class.java)

		if (RepositoryFragmentArgs.fromBundle(arguments).networkOnly) {
			viewModel.isNetworkOnly = true
			(activity as NavigationActivity).supportActionBar?.title = "Pagination Without Room"
		} else {
			(activity as NavigationActivity).supportActionBar?.title = "Pagination With Room"
		}

		setupRecyclerView()
		setupSubscribers()
	}

	private fun setupRecyclerView() {
		repositoryAdapter = RepositoryAdapter(object :
				RepositoryAdapter.ScrollListener {
			override fun showEndProgressView(visibility: Int) {
				if (binding.progressBar.visibility != visibility) {
					binding.progressBar.visibility = visibility
				}
			}
		})

		binding.recyclerView.adapter = repositoryAdapter
	}

	private fun setupSubscribers() {
		viewModel.data.observe(this, Observer {
			repositoryAdapter.submitList(it)
		})

		viewModel.apiStatus.observe(this, Observer {
			it?.let { apiStatus ->
				when (apiStatus.status) {
					Status.LOADING -> {
						binding.progressBar.visibility = View.VISIBLE
					}
					Status.SUCCESS -> {
						binding.progressBar.visibility = View.GONE
					}
					Status.ERROR -> {
						binding.progressBar.visibility = View.GONE
						binding.root.showSnack(apiStatus.msg)
					}
				}
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
				binding.progressBar.visibility = View.VISIBLE
				val updateQuery = "%${query.replace(' ', '%')}%"
				viewModel.query.postValue(updateQuery)
				repositoryAdapter.submitList(null)
				dismissKeyboard()
				return false
			}

			override fun onQueryTextChange(newText: String): Boolean {
				return false
			}
		})
	}
}
