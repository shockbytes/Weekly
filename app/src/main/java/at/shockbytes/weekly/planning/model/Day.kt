package at.shockbytes.weekly.planning.model

import com.google.common.base.MoreObjects

/**
 * @author  Martin Macheiner
 * Date:    19.06.2016
 */
class Day(var entries: MutableList<Task>) {

    override fun toString(): String {
        val helper = MoreObjects.toStringHelper(Day::class.java)
        for (wde in entries) {
            helper.add("Task", wde.toString())
        }
        return helper.toString()
    }
}
