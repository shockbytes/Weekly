package at.shockbytes.weekly.ui.fragment


import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import at.shockbytes.weekly.R
import at.shockbytes.weekly.adapter.WeekHeaderAdapter
import at.shockbytes.weekly.dagger.AppComponent
import kotterknife.bindView

class WeekFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_week

    private val recyclerViewDays: RecyclerView by bindView(R.id.fragment_week_rv_days)
    private val recyclerViewContent: RecyclerView by bindView(R.id.fragment_week_rv_content)

    private val headerLayoutManager: RecyclerView.LayoutManager
        get() = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        else
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

    override fun setupViews() {

        // Setup days
        val daysAdapter = WeekHeaderAdapter(context)
        recyclerViewDays.isNestedScrollingEnabled = false
        recyclerViewDays.layoutManager = headerLayoutManager
        recyclerViewDays.adapter = daysAdapter
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    companion object {

        fun newInstance(): WeekFragment {
            val fragment = WeekFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
