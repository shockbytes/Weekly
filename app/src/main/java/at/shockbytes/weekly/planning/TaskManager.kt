package at.shockbytes.weekly.planning

import at.shockbytes.weekly.planning.model.Task
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */

interface TaskManager {

    fun currentDayTasks(): Observable<List<Task>>

    fun storeTask(t: Task): Completable

    fun removeTask(t: Task): Completable

    fun updateTask(t: Task): Completable

}