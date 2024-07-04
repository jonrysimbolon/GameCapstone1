package id.zoneordering.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import id.zoneordering.core.BuildConfig
import id.zoneordering.core.data.DigimonRepository
import id.zoneordering.core.data.source.local.LocalDataSource
import id.zoneordering.core.data.source.local.room.DigimonDatabase
import id.zoneordering.core.data.source.remote.RemoteDataSource
import id.zoneordering.core.data.source.remote.network.ApiService
import id.zoneordering.core.domain.repository.IDigimonRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val Context.dataStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(name = "settings")

const val BASE_URL = "https://digimon-api.vercel.app/"
val HOST_NAME = BASE_URL.replace("https://", "").replace("/", "")

const val PIN1 = "sha256/vvCFPyhPFGdw/WkEzsDSSnww65aoKvdemtkuaCF1Qhc="
const val PIN2 = "sha256/bdrBhpj38ffhxpubzkINl0rG+UyossdhcBYj+Zx2fcc="
const val PIN3 = "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M="

val preferencesModule = module {
    single {
        androidContext().dataStore
    }
}

val databaseModule = module {
    factory { get<DigimonDatabase>().digimonDao() }
    single {
        val digimonKey = BuildConfig.DIGIMON_KEY
        val passphrase: ByteArray = SQLiteDatabase.getBytes(digimonKey.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            DigimonDatabase::class.java, "Digimon.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val certificatePinner = CertificatePinner.Builder()
            .add(HOST_NAME, PIN1)
            .add(HOST_NAME, PIN2)
            .add(HOST_NAME, PIN3)
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("${BASE_URL}api/")
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