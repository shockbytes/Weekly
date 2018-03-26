package at.shockbytes.weekly.dagger

import at.shockbytes.weekly.ui.activity.MainActivity
import at.shockbytes.weekly.ui.fragment.PersonalFragment
import at.shockbytes.weekly.ui.fragment.TodayFragment
import at.shockbytes.weekly.ui.fragment.WeekFragment
import dagger.Component
import javax.inject.Singleton

/**
 * @author Martin Macheiner
 * Date: 26-Mar-18.
 */
@Singleton
@Component(modules = [AppModule::class, FirebaseModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: TodayFragment)

    fun inject(fragment: WeekFragment)

    fun inject(fragment: PersonalFragment)

}