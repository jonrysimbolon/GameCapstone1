package id.zoneordering.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import id.zoneordering.core.data.DigimonRepository
import id.zoneordering.core.data.source.local.LocalDataSource
import id.zoneordering.core.data.source.local.room.DigimonDatabase
import id.zoneordering.core.data.source.remote.RemoteDataSource
import id.zoneordering.core.data.source.remote.network.ApiService
import id.zoneordering.core.domain.repository.IDigimonRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")

val preferencesModule = module {
    single {
        androidContext().dataStore
    }
}

val databaseModule = module {
    factory { get<DigimonDatabase>().digimonDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            DigimonDatabase::class.java, "Digimon.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://digimon-api.vercel.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IDigimonRepository> {
        DigimonRepository(
            get(),
            get()
        )
    }
}