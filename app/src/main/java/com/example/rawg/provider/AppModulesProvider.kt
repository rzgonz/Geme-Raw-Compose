package com.example.rawg.provider


import com.common.common.config.CommonBuildConfigProvider
import com.common.common.ext.clazz
import com.common.network.NetworkProvider
import com.common.presistance.PersistenceProvider
import org.koin.core.module.Module

class AppModulesProvider private constructor(
    private val isDebug: Boolean,
    private val defaultPackageName: String,
    private val configClassName: String
) {

    val appModules: List<Module>
        get() {
            return ArrayList<Module>().apply {
                addAll(commonBuildConfigModules)
                addAll(networkModules)
                addAll(persistenceProvider)
            }
        }


    private val commonBuildConfigModules by lazy {
        CommonBuildConfigProvider.getInstance(configClassName).modules
    }


    private val networkModules by lazy {
        NetworkProvider.getInstance().modules
    }

    private val persistenceProvider by lazy {
        PersistenceProvider.getInstance().modules
    }



    companion object {

        @Volatile
        private var INSTANCE: AppModulesProvider? = null

        @JvmStatic
        fun getInstance(
            isDebug: Boolean,
            defaultPackageName: String,
            configClassName: String
        ): AppModulesProvider {
            return INSTANCE ?: synchronized(clazz<AppModulesProvider>()) {
                return@synchronized AppModulesProvider(isDebug, defaultPackageName, configClassName)
            }.also {
                INSTANCE = it
            }
        }

    }
}