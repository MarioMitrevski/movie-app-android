package movieapp.app.util

import io.reactivex.rxjava3.core.SingleTransformer
import movieapp.app.exceptions.ApiException
import retrofit2.Response

fun <T> unWrapResponseWithError(): SingleTransformer<Response<T>, T> {
    return SingleTransformer { observable ->
        observable.map { response ->
            when {
                response.isSuccessful -> response.body()
                else -> throw ApiException(response.code(), response.message())
            }
        }
    }
}
