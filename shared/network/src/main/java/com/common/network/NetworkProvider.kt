package com.common.network

import com.common.common.ext.clazz
import com.common.common.provider.BaseModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


class NetworkProvider private constructor() : BaseModuleProvider {

    override val modules: List<Module>
        get() = listOf(networkModule)

    private val networkModule = module {
        single { NetworkModule.getClient() }
        single { NetworkUtils(context = androidContext(), commonBuildConfig = get()) }
        single { NetworkModule.getRetrofit(okHttpClient = get()) }
    }

    companion object {

        @Volatile
        private var INSTANCE: NetworkProvider? = null

        @JvmStatic
        fun getInstance(): NetworkProvider {
            return INSTANCE ?: synchronized(clazz<NetworkProvider>()) {
                return@synchronized NetworkProvider()
            }.also {
                INSTANCE = it
            }
        }

    }
}