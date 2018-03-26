package at.shockbytes.weekly.ui.fragment


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import at.shockbytes.weekly.R
import at.shockbytes.weekly.adapter.WeekDayAdapter
import at.shockbytes.weekly.dagger.AppComponent
import at.shockbytes.weekly.task.model.Day
import at.shockbytes.weekly.task.model.Task
import at.shockbytes.weekly.view.TodayItemTouchHelper
import kotterknife.bindView


class TodayFragment : BaseFragment(), WeekDayAdapter.OnItemClickListener, WeekDayAdapter.OnItemMoveListener {

    override val layoutId = R.layout.fragment_day

    private val recyclerView: RecyclerView by bindView(R.id.fragment_day_recyclerview)

    private var day: Day? = null

    private lateinit var adapter: WeekDayAdapter

    override fun setupViews() {
        recyclerView.layoutManager = getLayoutManager(context)
        adapter = WeekDayAdapter(activity, day?.entries)
        adapter.setOnItemClickListener(this)
        adapter.setOnItemMoveListener(this)
        val callback = TodayItemTouchHelper(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onItemClick(entity: Task) {
        // TODO
    }

    override fun onItemMove(entry: Task?, from: Int, to: Int) {
        // TODO
    }

    override fun onItemDismissed(entry: Task?) {
        // TODO
    }

    // TODO Call from Activity
    fun onFabPressed() {
        recyclerView.smoothScrollToPosition(0)
        // fabNew.hide();

        fragmentManager?.beginTransaction()
                ?.add(R.id.fragment_day_container, NewEntryFragment.newInstance())
                ?.commit()
    }

    private fun getLayoutManager(cxt: Context?): RecyclerView.LayoutManager {
        return if (cxt?.resources?.configuration?.orientation == Configuration.ORIENTATION_LANDSCAPE)
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        else
            LinearLayoutManager(cxt, LinearLayoutManager.VERTICAL, false)
    }

    companion object {

        fun newInstance(): TodayFragment {
            val fragment = TodayFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
