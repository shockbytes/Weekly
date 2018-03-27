package at.shockbytes.weekly.user

import android.graphics.Bitmap
import io.reactivex.Single

/**
 * Author:  Martin Macheiner
 * Date:    04.03.2018
 */
interface UserManager {

    val user: WeeklyUser

    fun signOut()

    fun loadAccountImage(): Single<Bitmap>

}