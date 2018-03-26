package at.shockbytes.weekly.ui.activity

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import at.shockbytes.weekly.ui.activity.core.ContainerBackNavigableActivity
import at.shockbytes.weekly.ui.fragment.SettingsFragment

class SettingsActivity : ContainerBackNavigableActivity() {

    override val displayFragment = SettingsFragment()

    companion object {

        fun newIntent(cxt: Context): Intent {
            return Intent(cxt, SettingsActivity::class.java)
        }
    }

}
