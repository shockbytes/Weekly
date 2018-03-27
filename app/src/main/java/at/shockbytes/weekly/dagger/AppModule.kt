package at.shockbytes.weekly.dagger

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import at.shockbytes.weekly.planning.FirebaseRoadmapManager
import at.shockbytes.weekly.planning.FirebaseTaskManager
import at.shockbytes.weekly.planning.RoadmapManager
import at.shockbytes.weekly.planning.TaskManager
import at.shockbytes.weekly.user.FirebaseUserManager
import at.shockbytes.weekly.user.UserManager
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */
@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideTaskManager(firebase: FirebaseDatabase): TaskManager {
        return FirebaseTaskManager(firebase)
    }

    @Provides
    @Singleton
    fun provideUserManager(): UserManager {
        return FirebaseUserManager(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideRoadmapManager(): RoadmapManager {
        return FirebaseRoadmapManager(app.applicationContext)
    }



}