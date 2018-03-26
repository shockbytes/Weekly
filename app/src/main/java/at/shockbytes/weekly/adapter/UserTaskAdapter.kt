package at.shockbytes.weekly.adapter

import android.content.Context

import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import at.shockbytes.util.adapter.BaseAdapter
import at.shockbytes.weekly.R
import at.shockbytes.weekly.task.model.Task
import kotterknife.bindView

class UserTaskAdapter(context: Context, extData: List<Task>) : BaseAdapter<Task>(context, extData.toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter<Task>.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.adapter_usertask, parent, false))
    }

    inner class ViewHolder(itemView: View) : BaseAdapter<Task>.ViewHolder(itemView) {

        private val textTitle: TextView by bindView(R.id.adapter_usertask_txt_title)
        private val textContent: TextView by bindView(R.id.adapter_usertask_txt_content)

        override fun bind(t: Task) {
            content = t

            textTitle.text = t.title
            textContent.text = t.content
        }
    }

}


