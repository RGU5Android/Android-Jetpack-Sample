package rgu5android.jetpack.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_navigation.buttonSearchRepositoryWithRoom
import kotlinx.android.synthetic.main.fragment_navigation.buttonSearchRepositoryWithoutRoom
import kotlinx.android.synthetic.main.fragment_navigation.buttonSearchUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rgu5android.jetpack.R
import rgu5android.jetpack.data.ApiService
import rgu5android.jetpack.extensions.showSnack
import javax.inject.Inject

class NavigationFragment : DaggerFragment() {

	@Inject
	lateinit var apiService: ApiService

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_navigation, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		buttonSearchUser.setOnClickListener {
			findNavController().navigate(
					NavigationFragmentDirections.ActionNavigationFragmentToGithubUserFragment()
			)
		}

		buttonSearchRepositoryWithRoom.setOnClickListener {
			val directions =
				NavigationFragmentDirections.ActionNavigationFragmentToRepositoryFragment()
			directions.setNetworkOnly(false)
			findNavController().navigate(directions)
		}

		buttonSearchRepositoryWithoutRoom.setOnClickListener {
			val directions =
				NavigationFragmentDirections.ActionNavigationFragmentToRepositoryFragment()
			directions.setNetworkOnly(true)
			findNavController().navigate(directions)
		}

		apiService.authenticate()
				.enqueue(object : Callback<JsonObject> {
					override fun onFailure(
						call: Call<JsonObject>,
						t: Throwable
					) {
						view?.showSnack("Authentication failed.")
					}

					override fun onResponse(
						call: Call<JsonObject>,
						response: Response<JsonObject>
					) {
						view?.showSnack("Authenticated for Github")
					}
				})
	}
}
