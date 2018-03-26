package at.shockbytes.weekly.dagger

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */
@Module
class FirebaseModule {

    @Provides
    @Singleton
    fun provideRealtimeDatabase(): FirebaseDatabase {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        return FirebaseDatabase.getInstance().reference.database
    }


}