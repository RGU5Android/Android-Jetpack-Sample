package rgu5android.jetpack.helper

@Suppress("DataClassPrivateConstructor")

enum class Status {
	LOADING,
	SUCCESS,
	ERROR
}

data class ApiStatus(
	val status: Status,
	val msg: String
) {

	companion object {
		val LOADING: ApiStatus =
			ApiStatus(Status.LOADING, "Loading")
		val SUCCESS: ApiStatus =
			ApiStatus(Status.SUCCESS, "Success")
		val ERROR: ApiStatus =
			ApiStatus(Status.ERROR, "")
	}
}