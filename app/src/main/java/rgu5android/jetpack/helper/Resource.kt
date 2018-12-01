package rgu5android.jetpack.helper

data class Resource<out T>(
	val apiStatus: ApiStatus,
	val data: T?
) {
	companion object {
		fun <T> success(data: T?): Resource<T> {
			return Resource(ApiStatus.SUCCESS, data)
		}

		fun <T> error(
			msg: String,
			data: T?
		): Resource<T> {
			return Resource(
					ApiStatus.ERROR.copy(msg = msg),
					data
			)
		}

		fun <T> loading(data: T?): Resource<T> {
			return Resource(ApiStatus.LOADING, data)
		}
	}
}
