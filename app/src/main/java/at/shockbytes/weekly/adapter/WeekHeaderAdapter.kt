package at.shockbytes.weekly.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import at.shockbytes.util.adapter.BaseAdapter
import at.shockbytes.weekly.R
import kotterknife.bindView

class WeekHeaderAdapter(context: Context)
    : BaseAdapter<String>(context, context.resources.getStringArray(R.array.week_days_short).toMutableList()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.adapter_week_header, parent, false))
    }

    inner class ViewHolder(itemView: View) : BaseAdapter<String>.ViewHolder(itemView) {

        private val textTitle: TextView by bindView(R.id.adapter_weekheader_text)

        override fun bind(t: String) {
            content = t
            textTitle.text = t
        }

    }

}


