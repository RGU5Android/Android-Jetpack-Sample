package rgu5android.jetpack.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.android.support.DaggerFragment

fun DaggerFragment.dismissKeyboard() {
	try {
		val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
		imm?.hideSoftInputFromWindow(view?.windowToken, 0)
	} catch (e: Exception) {
		// Ignored
	}
}