package at.shockbytes.weekly.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import at.shockbytes.util.AppUtils
import at.shockbytes.weekly.R
import at.shockbytes.weekly.dagger.AppComponent
import at.shockbytes.weekly.ui.activity.core.BaseActivity
import at.shockbytes.weekly.ui.fragment.RoadmapFragment
import at.shockbytes.weekly.ui.fragment.TodayFragment
import at.shockbytes.weekly.ui.fragment.WeekFragment
import at.shockbytes.weekly.user.UserManager
import com.google.firebase.auth.FirebaseAuth
import kotterknife.bindView
import javax.inject.Inject

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val appBar: AppBarLayout by bindView(R.id.main_appbar)
    private val toolbar: Toolbar by bindView(R.id.toolbar)
    private val floatingActionButton: FloatingActionButton by bindView(R.id.main_fab_edit)
    private val bottomNavigationView: BottomNavigationView by bindView(R.id.main_bottom_navigation)

    private lateinit var menuItemAccount: MenuItem

    @Inject
    protected lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.menu_navigation_week
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuItemAccount = menu.findItem(R.id.action_account)

        // Do this here, because here is the only place where
        // the menu item is already initialized
        setupPersonalMenuItem()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this)
        when (item.itemId) {

            R.id.action_logout -> {

                FirebaseAuth.getInstance().signOut()
                startActivity(LoginActivity.newIntent(this), options.toBundle())
                supportFinishAfterTransition()
            }

            R.id.action_settings ->
                startActivity(SettingsActivity.newIntent(this), options.toBundle())
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        appBar.setExpanded(true, true)

        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
        when (item.itemId) {

            R.id.menu_navigation_today -> {
                floatingActionButton.show()
                ft.replace(R.id.main_content, TodayFragment.newInstance())
            }
            R.id.menu_navigation_week -> {
                floatingActionButton.hide()
                ft.replace(R.id.main_content, WeekFragment.newInstance())
            }
            R.id.menu_navigation_roadmap -> {
                floatingActionButton.hide()
                ft.replace(R.id.main_content, RoadmapFragment.newInstance())
            }
        }
        ft.commit()
        return true
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    // -----------------------------------------------------------------------------

    private fun setupPersonalMenuItem() {

        userManager.loadAccountImage()
                .map { bm -> AppUtils.createRoundedBitmap(this, bm) }
                .subscribe({ roundedIcon ->
                    menuItemAccount.icon = roundedIcon
                }, { throwable: Throwable -> throwable.printStackTrace() })

        menuItemAccount.title = userManager.user.name
        menuItemAccount.isEnabled = true
    }

    // -----------------------------------------------------------------------------

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
