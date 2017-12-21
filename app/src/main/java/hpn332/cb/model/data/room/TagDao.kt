package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import hpn332.cb.model.stucture.Tag

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    fun allTag(): LiveData<List<Tag>>
}