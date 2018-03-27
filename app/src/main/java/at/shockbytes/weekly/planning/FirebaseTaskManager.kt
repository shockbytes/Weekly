package at.shockbytes.weekly.planning

import android.util.Log
import at.shockbytes.weekly.planning.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */

class FirebaseTaskManager(private val firebase: FirebaseDatabase) : TaskManager, ChildEventListener {

    // TODO Access it to initialize it
    private val tasksRef: DatabaseReference by lazy {
        val user = FirebaseAuth.getInstance().currentUser
        val ref = firebase.getReference("users/" + user?.uid + "/tasks")
        ref.addChildEventListener(this)
        Log.wtf("Weekly", ref.toString())
        ref
    }

    private val taskSubject: BehaviorSubject<List<Task>> = BehaviorSubject.create()
    private val tasks: MutableList<Task> = mutableListOf() // For internal housekeeping

    override fun currentDayTasks(): Observable<List<Task>> {
        return taskSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun storeTask(t: Task): Completable {
        return Completable.fromAction {
            tasksRef.push().let { ref ->
                t.id = ref.key
                ref.setValue(t)
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun removeTask(t: Task): Completable {
        return Completable.fromAction {
            tasksRef.child(t.id).removeValue()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateTask(t: Task): Completable {
        return Completable.fromAction {
            tasksRef.child(t.id).setValue(t)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

    // ----------------------------------------------------------

    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
        // Do nothing...
    }

    override fun onCancelled(p0: DatabaseError?) {
        // Do nothing...
    }

    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
        val task = Task.fromSnapshot(p0)
        // TODO
        publishTasks()
    }

    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
        val task = Task.fromSnapshot(p0)
        if (task != null) {
            tasks.add(task)
            publishTasks()
        }
    }

    override fun onChildRemoved(p0: DataSnapshot?) {
        val task = Task.fromSnapshot(p0)
        if (task != null) {
            tasks.remove(task)
            publishTasks()
        }
    }

    // ----------------------------------------------------------
    private fun publishTasks() {
        taskSubject.onNext(tasks)
    }

}