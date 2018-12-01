package rgu5android.jetpack.ui.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import rgu5android.jetpack.R
import rgu5android.jetpack.databinding.ItemRepositoryBinding
import rgu5android.jetpack.vo.Repository

class RepositoryAdapter(private val scrollListener: ScrollListener) :
		PagedListAdapter<Repository, RepositoryAdapter.ViewHolder>(
				REPOSITORY_COMPARATOR
		) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding: ItemRepositoryBinding =
			DataBindingUtil.inflate(inflater, R.layout.item_repository, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(
		holder: ViewHolder,
		position: Int
	) {

		getItem(position)?.let {
			holder.bind(it)
			if (position == itemCount - 1) {
				scrollListener.showEndProgressView(View.VISIBLE)
			} else {
				scrollListener.showEndProgressView(View.GONE)
			}
		}
	}

	inner class ViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(
			binding.root
	) {
		fun bind(repository: Repository) {
			binding.vm = repository
		}
	}

	companion object {
		private val REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<Repository>() {
			override fun areItemsTheSame(
				oldItem: Repository,
				newItem: Repository
			): Boolean {
				return newItem.fullName == oldItem.fullName
			}

			override fun areContentsTheSame(
				oldItem: Repository,
				newItem: Repository
			): Boolean {
				return oldItem == newItem
			}
		}
	}

	interface ScrollListener {
		fun showEndProgressView(visibility: Int)
	}
}