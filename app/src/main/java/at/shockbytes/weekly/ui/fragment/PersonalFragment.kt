package at.shockbytes.weekly.ui.fragment


import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import at.shockbytes.weekly.R
import at.shockbytes.weekly.adapter.UserTaskAdapter
import at.shockbytes.weekly.dagger.AppComponent
import at.shockbytes.weekly.task.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotterknife.bindView


class PersonalFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_me

    private val headerView: RelativeLayout by bindView(R.id.fragment_me_headerview)
    private val imgViewAvatar: ImageView by bindView(R.id.fragment_me_img_avatar)
    private val txtGreeting: TextView by bindView(R.id.fragment_me_txt_name)
    private val txtTodos: TextView by bindView(R.id.fragment_me_txt_amount_todos)
    private val viewDivider: View by bindView(R.id.fragment_me_view_divider)
    private val recyclerViewTasks: RecyclerView by bindView(R.id.fragment_me_recyclerview)

    override fun setupViews() {
        setUserImage()
        showDividerIfNecessary()

        val greetings = resources.getString(R.string.user_greeting, FirebaseAuth.getInstance().currentUser?.displayName)
        txtGreeting.text = greetings

        val todos = resources.getString(R.string.user_amount_todo, 4)
        txtTodos.text = todos

        setupRecyclerView()
    }

    override fun injectToGraph(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    private fun showDividerIfNecessary() {
        // Only show divider when no elevation is available
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            viewDivider.visibility = View.VISIBLE
        }
    }

    private fun setupHeaderColor() {

        val bm = (imgViewAvatar.drawable as? BitmapDrawable)?.bitmap
        if (bm != null) {
            // Set background asynchronous
            Palette.from(bm).generate { p ->
                val swatch = p.lightMutedSwatch
                if (swatch != null) {
                    headerView.setBackgroundColor(swatch.rgb)
                    txtGreeting.setTextColor(swatch.titleTextColor)
                    txtTodos.setTextColor(swatch.bodyTextColor)
                } else {
                    headerView.setBackgroundColor(p.getLightMutedColor(
                            ContextCompat.getColor(context!!, R.color.primary_background)))
                }
            }
        }
    }

    private fun setUserImage() {
        Picasso.with(context)
                .load(FirebaseAuth.getInstance().currentUser?.photoUrl)
                .placeholder(R.drawable.ic_account)
                .into(imgViewAvatar, object : Callback {
                    override fun onError() {}

                    override fun onSuccess() {
                        setupHeaderColor()
                    }
                })
    }

    private fun setupRecyclerView() {

        // TODO Remove later!
        val todos = listOf(
                Task("4 things todo for today", "Do it"),
                Task("Be prepared for tomorrow", "Tomorrow you have 20 things to do"),
                Task("4 things todo for today", "Do it"),
                Task("Be prepared for tomorrow", "Tomorrow you have 20 things to do"),
                Task("4 things todo for today", "Do it"),
                Task("Be prepared for tomorrow", "Tomorrow you have 20 things to do"),
                Task("4 things todo for today", "Do it"),
                Task("Be prepared for tomorrow", "Tomorrow you have 20 things to do"),
                Task("4 things todo for today", "Do it"),
                Task("Be prepared for tomorrow", "Tomorrow you have 20 things to do"))

        val adapter = UserTaskAdapter(activity!!, todos)
        //adapter.setOnItemClickListener(this);
        //ItemTouchHelper.Callback callback = new FavAppsItemTouchHelper(mAdapter);
        //ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //touchHelper.attachToRecyclerView(recyclerView);
        // Only add divider when device is smartphone
        recyclerViewTasks.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        recyclerViewTasks.adapter = adapter
    }

    companion object {

        fun newInstance(): PersonalFragment {
            val fragment = PersonalFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
