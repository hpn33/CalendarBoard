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
     * get countOfProject of projects
     *
     * @return countOfProject of projects
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Project)

    /**
     * update one or more project
     *
     * @param item
     */
    @Update
    fun update(item: Project)

    /**
     * delete one or more project
     *
     * @param item
     */
    @Delete
    fun delete(item: List<Project>)

    /**
     * delete one project
     *
     * @param id
     */
    @Query("DELETE FROM project WHERE id = :id")
    fun deleteById(id: Int)
}