package rgu5android.jetpack.extensions

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(
	message: String,
	length: Int = Snackbar.LENGTH_LONG
) {
	try {
		if (message.length > 1) {
			Snackbar.make(this, message, length)
					.apply {
						setAction("Dismiss") { dismiss() }
						setActionTextColor(Color.CYAN)
						show()
					}
		}
	} catch (e: Exception) {
		// Ignored
	}
}

fun View.showToast(
	message: String,
	length: Int = Toast.LENGTH_SHORT
) {
	try {
		if (message.length > 1) {
			Toast.makeText(this.context, message, length)
					.show()
		}
	} catch (e: Exception) {
		// Ignored
	}
}