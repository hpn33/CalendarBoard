package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface BackLogDao {

    @Query("SELECT * FROM backlog")
    fun all(): LiveData<List<BackLog>>

    @Insert
    fun insert(item: List<BackLog>)

    @Update
    fun update(item: List<BackLog>)

    @Delete
    fun delete(item: List<BackLog>)
}