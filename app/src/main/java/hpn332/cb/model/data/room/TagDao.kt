package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface TagDao {

    @Query("SELECT * FROM tag")
    fun all(): LiveData<List<Tag>>

    @Insert
    fun insert(item: List<Tag>)

    @Update
    fun update(item: List<Tag>)

    @Delete
    fun delete(item: List<Tag>)
}