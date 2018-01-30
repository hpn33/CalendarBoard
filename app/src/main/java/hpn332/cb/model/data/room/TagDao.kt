package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.Tag

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface TagDao {

    /**
     * get all tag
     *
     * @return all tag
     */
    @Query("SELECT * FROM tag")
    fun all(): LiveData<List<Tag>>

    /**
     * get one tag by id
     *
     * @param id
     *
     * @return tag object
     */
    @Query("SELECT * FROM tag WHERE id = :id")
    fun one(id: Int): LiveData<Tag>

    @Insert
    fun insert(item: Tag)

    @Update
    fun update(item: Tag)

    @Delete
    fun delete(item: Tag)

    /**
     * delete one tag
     *
     * @param id
     */
    @Query("DELETE FROM tag WHERE id = :id")
    fun deleteById(id: Int)
}