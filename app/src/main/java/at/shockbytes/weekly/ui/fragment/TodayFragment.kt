package at.shockbytes.weekly.ui.fragment


import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import at.shockbytes.util.adapter.BaseAdapter
import at.shockbytes.util.adapter.BaseItemTouchHelper
import at.shockbytes.weekly.R
import at.shockbytes.weekly.adapter.TaskAdapter
import at.shockbytes.weekly.dagger.AppComponent
import at.shockbytes.weekly.planning.TaskManager
import at.shockbytes.weekly.planning.model.Task
import kotterknife.bindView
import javax.inject.Inject


class TodayFragment : BaseFragment(),
        BaseAdapter.OnItemClickListener<Task>, BaseAdapter.OnItemMoveListener<Task> {

    override val layoutId = R.layout.fragment_day

    @Inject
    protected lateinit var taskManager: TaskManager

    private val recyclerView: RecyclerView by bindView(R.id.fragment_day_recyclerview)

    private val layoutManager: RecyclerView.LayoutManager
        get() = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        else
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    private lateinit var adapter: TaskAdapter

    override fun setupViews() {
        recyclerView.layoutManager = layoutManager
        adapter = TaskAdapter(activity!!, listOf())
        adapter.onItemClickListener = this
        adapter.onItemMoveListener = this
        val callback = BaseItemTouchHelper(adapter, false, BaseItemTouchHelper.DragAccess.ALL)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = adapter
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onStart() {
        super.onStart()

        taskManager.currentDayTasks().subscribe { dayTasks ->
            adapter.data = dayTasks.toMutableList()
        }
    }

    override fun onItemDismissed(t: Task, position: Int) {
        // TODO
    }

    override fun onItemMove(t: Task, from: Int, to: Int) {
        // TODO
    }

    override fun onItemMoveFinished() {
        // TODO
    }

    override fun onItemClick(t: Task, v: View) {
        // TODO
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
