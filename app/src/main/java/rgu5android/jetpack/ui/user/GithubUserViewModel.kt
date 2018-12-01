package rgu5android.jetpack.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import rgu5android.jetpack.data.repository.GithubUserRepository
import rgu5android.jetpack.helper.AbsentLiveData
import rgu5android.jetpack.helper.Resource
import rgu5android.jetpack.vo.GithubUser
import javax.inject.Inject

class GithubUserViewModel @Inject constructor(
	private val repository: GithubUserRepository
) : ViewModel() {

	val data = MutableLiveData<String>()
	val imageUrl = MutableLiveData<String>()

	val query = MutableLiveData<String>()

	val response: LiveData<Resource<GithubUser>> = Transformations.switchMap(query) { value ->
		if (value.isNullOrBlank()) {
			AbsentLiveData.create()
		} else {
			repository.findUser(value)
		}
	}
}
