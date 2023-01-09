package com.example.rawg

import android.app.Application
import com.common.common.config.CommonBuildConfig
import com.common.network.NetworkUtils
import com.example.rawg.provider.AppModulesProvider
import com.example.rawg.provider.FeatureModulesProvider
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module


open class Applications : Application() {

    private val defaultPackageName: String by lazy { this.packageName }
    private val networkUtils: NetworkUtils by inject()
    private val commonBuildConfig: CommonBuildConfig by inject()


    private val configClassName: String by lazy {
        BuildConfig::class.java.`package`.name.orEmpty() + ".BuildConfig"
    }

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        networkUtils.setBaseUrl(
           "https://api.rawg.io/api/"
        )

    }

    private fun setupKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Applications)

            modules(mutableListOf<Module>().apply {
                addAll(
                    AppModulesProvider.getInstance(
                        isDebug = BuildConfig.DEBUG,
                        defaultPackageName = defaultPackageName,
                        configClassName = configClassName
                    ).appModules
                )
                plusAssign(FeatureModulesProvider.featureModules)
            })
        }
    }




}