package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Task

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun all(): LiveData<List<Task>>

    @Query("SELECT * FROM task")
    fun todo(): LiveData<List<Task>>

    @Query("SELECT * FROM task")
    fun doing(): LiveData<List<Task>>

    @Query("SELECT * FROM task")
    fun done(): LiveData<List<Task>>

    @Insert
    fun insert(item: List<Task>)

    @Update
    fun update(item: List<Task>)

    @Delete
    fun delete(item: List<Task>)
}