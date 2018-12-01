package rgu5android.jetpack.ui.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import rgu5android.jetpack.R
import rgu5android.jetpack.databinding.ItemFollowBinding
import rgu5android.jetpack.vo.Follow

class FollowAdapter : ListAdapter<Follow, FollowAdapter.ViewHolder>(
		FOLLOW_COMPARATOR
) {

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val binding: ItemFollowBinding =
			DataBindingUtil.inflate(inflater, R.layout.item_follow, parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(
		holder: ViewHolder,
		position: Int
	) {
		holder.bind(getItem(position))
	}

	class ViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(
			binding.root
	) {
		fun bind(item: Follow) {
			binding.vm = item
		}
	}

	companion object {
		private val FOLLOW_COMPARATOR = object : DiffUtil.ItemCallback<Follow>() {
			override fun areItemsTheSame(
				oldItem: Follow,
				newItem: Follow
			): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(
				oldItem: Follow,
				newItem: Follow
			): Boolean {
				return oldItem == newItem
			}
		}
	}
}