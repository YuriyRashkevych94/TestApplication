package com.example.testapplication.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.MutableLiveData
import com.example.testapplication.R
import com.example.testapplication.data.Error


class NetworkConnectionHelper(private val context: Context) :  ConnectivityManager.NetworkCallback() {

    companion object {
        const val ERROR_NO_INTERNET = 0
        const val ERROR_SERVER = 1
    }

    private val manager: ConnectivityManager?
            by lazy { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    private var isAvailable = false

    val liveData = MutableLiveData<Boolean>()

    private val networkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    init {
        manager?.registerNetworkCallback(networkRequest, this)
    }

    override fun onUnavailable() {
        super.onUnavailable()
        isAvailable = false
        liveData.postValue(isAvailable)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        super.onLosing(network, maxMsToLive)
        isAvailable = false
        liveData.postValue(isAvailable)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isAvailable = true
        liveData.postValue(isAvailable)
    }

    fun isOffline(): Boolean = !isAvailable

    fun getNoInternetError(): Error {
        return Error(ERROR_NO_INTERNET,
            context.getString(R.string.error_internet_title),
            context.getString(R.string.error_internet_message))
    }

    fun getServerError(): Error {
        return Error(ERROR_SERVER,
            context.getString(R.string.error_server_title),
            context.getString(R.string.error_server_message))
    }
}