package com.common.network

import com.common.common.config.CommonBuildConfig
import com.common.common.ext.logI
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object NetworkModule : KoinComponent {

    private val commonBuildConfig: CommonBuildConfig by inject()
    private val networkUtils: NetworkUtils by inject()

    private var requestTimeOut = 120L

    private const val API_KEY = "key"
    private const val API_KEY_VALUE = "576c6dcb71534553893309681569b92f"


    private val builder by lazy {
        logI<NetworkModule>("Init network")
        OkHttpClient.Builder()
    }

    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(networkUtils.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    fun getClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            logI<NetworkModule>("is debug ${commonBuildConfig.isDebug()}")
            level = if (commonBuildConfig.isDebug()) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
        val httpClient = builder.apply {
            applyTrustManagerOkHttp()
            //  certificatePinner(createCertificatePinier())
            addNetworkInterceptor(logInterceptor)
            addInterceptor(Interceptor {
                var request = it.request()
                val url: HttpUrl =
                    request.url().newBuilder().addQueryParameter(API_KEY, API_KEY_VALUE).build()
                request = request.newBuilder().url(url).build()
                it.proceed(request)
            })
            callTimeout(requestTimeOut, TimeUnit.SECONDS)
            connectTimeout(requestTimeOut, TimeUnit.SECONDS)
            readTimeout(requestTimeOut, TimeUnit.SECONDS)
            writeTimeout(requestTimeOut, TimeUnit.SECONDS)
        }

        return httpClient.build()
    }

}