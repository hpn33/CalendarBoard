package hpn332.cb.model.data.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import hpn332.cb.model.stucture.Task

/**
 * Created by hpn332 on 21/12/2017.
 */
@Dao
interface TaskDao {

    /**
     * get countOfProject of tasks
     *
     * @return int - countOfProject of tasks
     */
    @Query("SELECT COUNT(id) FROM task WHERE project_id = :project_id")
    fun countOfProject(project_id: Int): LiveData<Int>

    /**
     * get countOfProject of tasks
     *
     * @return int - countOfProject of tasks
     */
    @Query("SELECT COUNT(id) FROM task WHERE project_id = :project_id AND step = 3")
    fun countDoneOfProject(project_id: Int): LiveData<Int>

    /**
     * get all task
     *
     * @return all task
     */
    @Query("SELECT * FROM task")
    fun all(): LiveData<List<Task>>

    /**
     * get all task from project and step one
     *
     * @return list of task as step one
     */
    @Query("SELECT * FROM task WHERE step = 1 AND project_id = :project_id")
    fun todo(project_id: Int): LiveData<List<Task>>

    /**
     * get all task from project and step two
     *
     * @return list of task as step two
     */
    @Query("SELECT * FROM task WHERE step = 2 AND project_id = :project_id")
    fun doing(project_id: Int): LiveData<List<Task>>

    /**
     * get all task from project and step three
     *
     * @return list of task as step three
     */
    @Query("SELECT * FROM task WHERE step = 3 AND project_id = :project_id")
    fun done(project_id: Int): LiveData<List<Task>>

    /**
     * insert one task
     *
     * @param item
     */
    @Insert
    fun insert(item: Task)

    /**
     * update one task
     *
     * @param item
     */
    @Update
    fun update(item: Task)

    /**
     * delete one task
     *
     * @param item
     */
    @Delete
    fun delete(item: Task)

    /**
     * get one task by id
     *
     * @param id
     *
     * @return task object
     */
    @Query("SELECT * FROM task WHERE id = :id")
    fun one(id: Int): LiveData<Task>

    /**
     * delete one project
     *
     * @param id
     */
    @Query("DELETE FROM task WHERE id = :id")
    fun deleteById(id: Int)
}