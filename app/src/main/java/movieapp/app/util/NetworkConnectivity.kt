package movieapp.app.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED
import androidx.lifecycle.LifecycleObserver
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnectivity @Inject constructor(
    @ApplicationContext private val context: Context
) : LifecycleObserver {
    val networkChanges: BehaviorSubject<Boolean> = BehaviorSubject.create()
    var isNetworkAvailable = false

    init {
        initializeNetworkAvailability(context)
    }

    private fun initializeNetworkAvailability(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
            if (hasCapability(NET_CAPABILITY_VALIDATED)) {
                isNetworkAvailable = true
                networkChanges.onNext(isNetworkAvailable)
            }
        }
        connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
            override fun onAvailable(network: Network) {
                isNetworkAvailable = true
                networkChanges.onNext(isNetworkAvailable)
            }

            override fun onLost(network: Network) {
                isNetworkAvailable = false
                networkChanges.onNext(isNetworkAvailable)
            }

            override fun onUnavailable() {
                isNetworkAvailable = false
                networkChanges.onNext(isNetworkAvailable)
            }
        }
        )
    }
}