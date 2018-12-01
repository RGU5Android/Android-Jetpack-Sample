package rgu5android.jetpack.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import rgu5android.jetpack.data.repository.GithubUserRepository
import rgu5android.jetpack.helper.AbsentLiveData
import rgu5android.jetpack.helper.ApiResponse
import rgu5android.jetpack.vo.Follow
import javax.inject.Inject

class FollowViewModel @Inject constructor(
	private val repository: GithubUserRepository
) : ViewModel() {

	val input = MutableLiveData<Input>()

	val data: LiveData<ApiResponse<List<Follow>>> = Transformations.switchMap(input) {
		if (it != null) {
			if (it.type == 0) {
				repository.getFollowers(it.user)
			} else {
				repository.getFollowings(it.user)
			}
		} else {
			AbsentLiveData.create()
		}
	}

	class Input(
		val user: String,
		val type: Int
	)
}
