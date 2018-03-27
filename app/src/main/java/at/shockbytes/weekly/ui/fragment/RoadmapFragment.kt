package at.shockbytes.weekly.ui.fragment

import android.os.Bundle
import at.shockbytes.weekly.R
import at.shockbytes.weekly.dagger.AppComponent

/**
 * Author:  Martin Macheiner
 * Date:    27.03.2018
 */
class RoadmapFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_roadmap

    override fun setupViews() {
        // TODO
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {

        fun newInstance(): RoadmapFragment {
            val fragment = RoadmapFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
