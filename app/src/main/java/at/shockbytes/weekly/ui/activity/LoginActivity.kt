package at.shockbytes.weekly.ui.activity

import android.content.Context
import android.content.Intent
import at.shockbytes.weekly.ui.activity.core.ContainerBackNavigableActivityCompat
import at.shockbytes.weekly.ui.fragment.SignUpFragment

/**
 * http://stackoverflow.com/questions/29724590/simple-android-design-flow-inquiry-for-login-activity-and-main-activity
 *
 */
class LoginActivity : ContainerBackNavigableActivityCompat() {

    override val displayFragment = SignUpFragment.newInstance()

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }


}
