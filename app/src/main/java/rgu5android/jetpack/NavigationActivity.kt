package rgu5android.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.android.support.DaggerAppCompatActivity
import rgu5android.jetpack.databinding.ActivityNavigationBinding

class NavigationActivity : DaggerAppCompatActivity() {

	private lateinit var binding: ActivityNavigationBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)
		binding.setLifecycleOwner(this)

		setSupportActionBar(binding.toolbar)
		setupActionBarWithNavController(findNavController(R.id.navigation))
	}

	override fun onSupportNavigateUp() = findNavController(R.id.navigation).navigateUp()
}
