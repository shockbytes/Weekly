package at.shockbytes.weekly.user

import android.net.Uri

/**
 * Author:  Martin Macheiner
 * Date:    04.03.2018
 */

data class WeeklyUser(val name: String, val photoUrl: Uri?,
                      val email: String?, val providerId: String)