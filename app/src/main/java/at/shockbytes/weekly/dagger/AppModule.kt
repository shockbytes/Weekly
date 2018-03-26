package at.shockbytes.weekly.dagger

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import at.shockbytes.weekly.task.FirebaseTaskManager
import at.shockbytes.weekly.task.TaskManager
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


}