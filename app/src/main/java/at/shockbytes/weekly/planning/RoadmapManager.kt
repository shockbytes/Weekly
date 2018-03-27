package at.shockbytes.weekly.planning

import at.shockbytes.weekly.planning.model.Roadmap
import io.reactivex.Observable

/**
 * Author:  Martin Macheiner
 * Date:    27.03.2018
 */
interface RoadmapManager {

    fun roadmap(): Observable<Roadmap>

}