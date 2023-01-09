package com.example.rawg.provider


import androidx.room.Room
import com.common.common.ext.clazz
import com.common.common.provider.BaseModuleProvider
import com.example.rawg.data.local.AppDatabase
import com.example.rawg.data.remote.RawgRemoteDataSource
import com.example.rawg.data.remote.RawgWebServices
import com.example.rawg.domain.RawgInteractor
import com.example.rawg.domain.RawgInteractorImpl
import com.example.rawg.domain.RawgRepository
import com.example.rawg.data.local.RawgLocalDataSource
import com.example.rawg.data.local.entity.GameDao
import com.example.rawg.presentation.home.listGame.ListGameViewModel
import com.example.rawg.presentation.detail.DetailViewModel
import com.example.rawg.presentation.home.favorite.FavoriteViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit

class RawgProvider private constructor() : BaseModuleProvider {

    override val modules: List<Module>
        get() = listOf(introModule, interactorModule)

    private val introModule = module {
        single {
            provideWebService(retrofit = get())
        }

        single {
            RawgRemoteDataSource(rawgWebServices = get())
        }

        single{
            AppDatabase.getInstance(androidContext()).gameDao()
        }

        single {
            RawgLocalDataSource(gameDao = get())
        }

        single {
            RawgRepository(rawgRemoteDataSource = get(), rawgLocalDataSource = get())
        }


        // ViewModel for Detail View
        viewModel { ListGameViewModel(rawgInteractor = get()) }
        viewModel { DetailViewModel(rawgInteractor = get()) }
        viewModel { FavoriteViewModel(rawgInteractor = get()) }


    }

    private val interactorModule = module {

        factory {
            RawgInteractorImpl(
                rawgRepository = get ()
            )
        } binds arrayOf(RawgInteractor::class)
    }


    private fun provideWebService(retrofit: Retrofit) = retrofit.create(clazz<RawgWebServices>())

    companion object {

        @Volatile
        private var INSTANCE: RawgProvider? = null

        @JvmStatic
        fun getInstance(): RawgProvider {
            return INSTANCE ?: synchronized(clazz<RawgProvider>()) {
                return@synchronized RawgProvider()
            }.also {
                INSTANCE = it
            }
        }

    }


}