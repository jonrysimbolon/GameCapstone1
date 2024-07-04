package id.zoneordering.gamecapstone1

import android.app.Application
import com.scottyab.rootbeer.RootBeer
import id.zoneordering.core.di.databaseModule
import id.zoneordering.core.di.networkModule
import id.zoneordering.core.di.preferencesModule
import id.zoneordering.core.di.repositoryModule
import id.zoneordering.gamecapstone1.di.useCaseModule
import id.zoneordering.gamecapstone1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import kotlin.system.exitProcess

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val rootBeer = RootBeer(this@MyApplication)
        if(rootBeer.isRooted){
            println("Your device is rooted")
            exitProcess(0)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    preferencesModule,
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}