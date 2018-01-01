package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.Project

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface ProjectDao {

    @Query("SELECT * FROM project")
    fun all(): LiveData<List<Project>>

    @Insert
    fun insert(item: List<Project>)

    @Update
    fun update(item: List<Project>)

    @Delete
    fun delete(item: List<Project>)
}