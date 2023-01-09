package com.common.presistance

import com.common.common.ext.clazz
import com.common.common.provider.BaseModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

class PersistenceProvider : BaseModuleProvider {


    override val modules: List<Module>
        get() = listOf(localStorageModule)

    private val localStorageModule = module {
        single { BasePreference.getInstance(context = androidContext()) }
    }

    companion object {

        @Volatile
        private var INSTANCE: PersistenceProvider? = null

        @JvmStatic
        fun getInstance(): PersistenceProvider {
            return INSTANCE ?: synchronized(clazz<PersistenceProvider>()) {
                return@synchronized PersistenceProvider()
            }.also {
                INSTANCE = it
            }
        }

    }

}