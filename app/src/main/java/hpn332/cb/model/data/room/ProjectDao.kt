package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.Project

/**
 * Data Access Object for the project table.
 */
@Dao
interface ProjectDao {

    /**
     * get count of projects
     *
     * @return count of projects
     */
    @Query("SELECT COUNT(id) FROM project")
    fun count(): Int

    /**
     * get all projects
     *
     * @return all projects
     */
    @Query("SELECT * FROM project")
    fun all(): LiveData<List<Project>>

    /**
     * get one project by id
     *
     * @param id
     *
     * @return one project
     */
    @Query("SELECT * FROM project WHERE id = :id")
    fun one(id: Int): LiveData<Project>

    /**
     * insert one or more project
     *
     * @param item
     */
    @Insert
    fun insert(item: List<Project>)

    /**
     * update one or more project
     *
     * @param item
     */
    @Update
    fun update(item: List<Project>)

    /**
     * delete one or more project
     *
     * @param item
     */
    @Delete
    fun delete(item: List<Project>)
}