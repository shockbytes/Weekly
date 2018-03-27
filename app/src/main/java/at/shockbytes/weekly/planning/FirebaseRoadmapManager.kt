package at.shockbytes.weekly.planning

import android.content.Context
import android.util.Log
import at.shockbytes.weekly.planning.model.Roadmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Author:  Martin Macheiner
 * Date:    27.03.2018
 */
class FirebaseRoadmapManager(private val context: Context, private val firebase: FirebaseDatabase)
    : RoadmapManager, ChildEventListener {

    // TODO Access it to initialize it
    private val roadmapRef: DatabaseReference by lazy {
        val user = FirebaseAuth.getInstance().currentUser
        val ref = firebase.getReference("users/" + user?.uid + "/roadmap")
        ref.addChildEventListener(this)
        Log.wtf("Weekly", ref.toString())
        ref
    }

    private val roadmapSubject: BehaviorSubject<Roadmap> = BehaviorSubject.create()
    private val roadmap: Roadmap = Roadmap() // for internal housekeeping


    override fun roadmap(): Observable<Roadmap> {
        return roadmapSubject
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    // ----------------------------------------------------------

    override fun onCancelled(p0: DatabaseError?) {
        // Do nothing...
    }


    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
        // Do nothing...
    }

    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
        // TODO Update roadmap
        publishRoadmap()
    }

    override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
        // TODO Update roadmap
        publishRoadmap()
    }

    override fun onChildRemoved(p0: DataSnapshot?) {
        // TODO Update roadmap
        publishRoadmap()
    }

    // ----------------------------------------------------------
    private fun publishRoadmap() {
        roadmapSubject.onNext(roadmap)
    }

}