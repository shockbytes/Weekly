package at.shockbytes.weekly.planning.model

import com.google.common.base.MoreObjects
import com.google.firebase.database.DataSnapshot

/**
 * Author:  Martin Macheiner
 * Date:    19.06.2016
 */
class Task(
        var title: String = "",
        var content: String = "",
        var id: String = "",
        var label: String? = null,
        var dayPosition: Int = 0,
        var day: Int = 0,
        var isImportant: Boolean = false,
        var isReminderSet: Boolean = false,
        var reminderTime: Long = 0) {


    fun hasLabel(): Boolean {
        return label != null
    }

    override fun toString(): String {
        return MoreObjects.toStringHelper(Task::class.java)
                .add("Title", title)
                .add("Content", content)
                .add("Id", id)
                .toString()
    }

    companion object {

        fun fromSnapshot(snapshot: DataSnapshot?): Task? {
            return snapshot?.getValue(Task::class.java)
        }
    }

}
