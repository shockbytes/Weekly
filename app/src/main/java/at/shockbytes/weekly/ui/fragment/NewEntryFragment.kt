package at.shockbytes.weekly.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.EditText
import android.widget.RelativeLayout
import at.shockbytes.weekly.R
import at.shockbytes.weekly.planning.model.Task
import at.shockbytes.weekly.view.ViewManager
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import kotterknife.bindView

/**
 * Author:  Martin Macheiner
 * Date:    06.07.2016.
 */
class NewEntryFragment : Fragment() {

    interface OnNewEntryCallbackListener {

        fun onNewEntry(entry: Task)

        fun closeView()
    }

    private var listener: OnNewEntryCallbackListener? = null

    private val editTitle: EditText by bindView(R.id.newitem_txt_title)
    private val editDescription: EditText by bindView(R.id.newitem_txt_description)

    private var unbinder: Unbinder? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as? OnNewEntryCallbackListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_new_entry, container, false) as RelativeLayout
        unbinder = ButterKnife.bind(this, layout)

        // Expand view
        ViewManager.expand(layout)

        // Fade in all views
        for (i in 0 until layout.childCount) {
            val v = layout.getChildAt(i)
            val animator = v.animate()
                    .scaleX(1f).scaleY(1f)
                    .setDuration(300)
            animator.startDelay = (i * 50).toLong()
            animator.start()
        }
        return layout
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
    }

    @OnClick(R.id.newitem_imgbtn_close)
    fun onClickClose() {

        ViewManager.collapse(view, object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                fragmentManager?.beginTransaction()?.remove(this@NewEntryFragment)?.commit()
            }

            override fun onAnimationRepeat(animation: Animation) {
            }
        })
        listener?.closeView()
    }

    @OnClick(R.id.newitem_btn_next)
    fun onClickNext() {

        val entry = Task(editTitle.text.toString(),
                editDescription.text.toString())
        listener?.onNewEntry(entry)

        editTitle.setText("")
        editDescription.setText("")
    }

    @OnClick(R.id.newitem_btn_done)
    fun onClickDone() {

        val entry = Task(editTitle.text.toString(), editDescription.text.toString())
        listener?.onNewEntry(entry)

        onClickClose()
    }

    companion object {

        fun newInstance(): NewEntryFragment {
            return NewEntryFragment()
        }
    }

}
