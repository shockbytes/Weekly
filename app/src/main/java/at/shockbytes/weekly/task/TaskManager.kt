package at.shockbytes.weekly.task

import at.shockbytes.weekly.task.model.Task
import io.reactivex.Observable

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */

interface TaskManager {

    fun currentDayTasks(): Observable<List<Task>>

}