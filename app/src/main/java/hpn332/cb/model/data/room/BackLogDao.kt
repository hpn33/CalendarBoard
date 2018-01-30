package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.BackLog

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface BackLogDao {

    /**
     * get one backlog by id
     *
     * @param id
     *
     * @return backlog object
     */
    @Query("SELECT * FROM backlog WHERE id = :id")
    fun one(id: Int): LiveData<BackLog>

    @Query("SELECT * FROM backlog")
    fun all(): LiveData<List<BackLog>>

    @Insert
    fun insert(item: BackLog)

    @Update
    fun update(item: BackLog)

    @Delete
    fun delete(item: BackLog)

    /**
     * delete one backlog
     *
     * @param id
     */
    @Query("DELETE FROM backlog WHERE id = :id")
    fun deleteById(id: Int)
}