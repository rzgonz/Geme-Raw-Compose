package com.common.common.config

import com.common.common.ext.clazz
import com.common.common.provider.BaseModuleProvider
import org.koin.core.module.Module
import org.koin.dsl.module


class CommonBuildConfigProvider private constructor(private val configClassName: String) :
    BaseModuleProvider {

    override val modules: List<Module>
        get() = listOf(
            commonBuildConfigModule
        )

    private val commonBuildConfigModule = module {
        single { CommonBuildConfig(configClassName) }
    }

    companion object {

        @Volatile
        private var INSTANCE: CommonBuildConfigProvider? = null

        @JvmStatic
        fun getInstance(configClassName: String): CommonBuildConfigProvider {
            return INSTANCE ?: synchronized(clazz<CommonBuildConfigProvider>()) {
                return@synchronized CommonBuildConfigProvider(configClassName)
            }.also {
                INSTANCE = it
            }
        }

    }
}