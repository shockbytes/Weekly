package at.shockbytes.weekly.task

import android.util.Log
import at.shockbytes.weekly.task.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * Author:  Martin Macheiner
 * Date:    26.03.2018
 */

class FirebaseTaskManager(private val firebase: FirebaseDatabase) : TaskManager, ChildEventListener {

    // TODO Access it to initialize it
    private val databaseRef: DatabaseReference by lazy {
        val user = FirebaseAuth.getInstance().currentUser
        val ref = firebase.getReference("users/" + user?.uid + "/entries")
        ref.addChildEventListener(this)
        Log.wtf("Weekly", ref.toString())
        ref
    }

    private val tasks: MutableList<Task> = mutableListOf()

    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
        // Do nothing...
    }

    override fun onCancelled(p0: DatabaseError?) {
        // Do nothing...
    }

    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
        val task = Task.fromSnapshot(p0)
        // TODO
    }

    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
        val task = Task.fromSnapshot(p0)
        if (task != null) {
            tasks.add(task)
        }
    }

    override fun onChildRemoved(p0: DataSnapshot?) {
        val task = Task.fromSnapshot(p0)
        tasks.remove(task)
    }

}