package at.shockbytes.weekly.ui.fragment


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.widget.Button
import at.shockbytes.weekly.R
import at.shockbytes.weekly.dagger.AppComponent
import at.shockbytes.weekly.ui.activity.MainActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotterknife.bindView


class SignUpFragment : BaseFragment() {

    private val btnLogin: Button by bindView(R.id.act_login_btn_sync)

    override val layoutId = R.layout.fragment_login

    override fun setupViews() {
        btnLogin.setOnClickListener { login() }
    }

    override fun injectToGraph(appComponent: AppComponent) {
        // Do nothing...
    }

    override fun onStart() {
        super.onStart()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            openMainActivityWithoutAnimation()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            openMainActivity()
        }
    }


    private fun login() {

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(listOf(
                                AuthUI.IdpConfig.GoogleBuilder()
                                        .build(),
                                AuthUI.IdpConfig.EmailBuilder()
                                        .setAllowNewAccounts(true)
                                        .setRequireName(true)
                                        .build()))
                        .setIsSmartLockEnabled(true)
                        .setLogo(R.mipmap.ic_launcher)
                        .build(),
                RC_SIGN_IN)
    }

    private fun openMainActivity() {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!)
        startActivity(MainActivity.newIntent(activity!!), options.toBundle())
        activity?.supportFinishAfterTransition()
    }

    private fun openMainActivityWithoutAnimation() {
        startActivity(MainActivity.newIntent(activity!!))
        activity?.supportFinishAfterTransition()
    }

    companion object {

        private const val RC_SIGN_IN = 0x2341

        fun newInstance(): SignUpFragment {
            val fragment = SignUpFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
