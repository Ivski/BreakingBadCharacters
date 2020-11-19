package rocks.ivski.bbc.utils

data class ApiResult<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): ApiResult<T> {
            return ApiResult(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): ApiResult<T> {
            return ApiResult(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): ApiResult<T> {
            return ApiResult(Status.LOADING, data, null)
        }

    }

}