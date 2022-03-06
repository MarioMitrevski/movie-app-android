package movieapp.app.util

import android.content.res.Resources
import io.reactivex.rxjava3.core.SingleTransformer
import movieapp.app.R
import movieapp.app.exceptions.ApiException
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkResponseMapper @Inject constructor(
    private val resources: Resources
) {
    fun <T> unWrapResponseWithError(): SingleTransformer<Response<T>, T> {
        return SingleTransformer { observable ->
            observable.map { response ->
                when {
                    response.isSuccessful -> response.body()
                    else -> throw ApiException(
                        response.code(),
                        resources.getString(R.string.generic_error_message)
                    )
                }
            }
        }
    }
}