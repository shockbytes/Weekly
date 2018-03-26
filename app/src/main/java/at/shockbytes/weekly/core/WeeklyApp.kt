package at.shockbytes.weekly.core

import android.app.Application
import at.shockbytes.weekly.dagger.AppComponent
import at.shockbytes.weekly.dagger.AppModule
import at.shockbytes.weekly.dagger.DaggerAppComponent
import at.shockbytes.weekly.dagger.FirebaseModule

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */

class WeeklyApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .firebaseModule(FirebaseModule())
                .build()
    }

}