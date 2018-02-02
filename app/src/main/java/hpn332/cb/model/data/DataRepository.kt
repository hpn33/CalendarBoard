package hpn332.cb.model.data

import android.arch.lifecycle.LiveData
import hpn332.cb.App.Companion.db
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task
import hpn332.cb.utils.helper.ExecutorHelper.dickIO

/**
 *
 */
class DataRepository {

    private val project = db.projectDao()
    private val task = db.taskDao()
    private val tag = db.tagDao()
    private val backlog = db.backlogDao()

    // region PROJECT -------------

    fun allProject(): LiveData<List<Project>> = project.all()
    fun getProjectById(id: Int): LiveData<Project> = project.one(id)

    fun insertProject(item: Project) = dickIO.execute { project.insert(item) }
    fun updateProject(item: Project) = dickIO.execute { project.update(item) }
    fun deleteProjectById(id: Int) = dickIO.execute { project.deleteById(id) }

    // endregion ----------------

    // region TASK --------------

    fun getCountTaskOfProject(project_id: Int): LiveData<Int> =
        task.countOfProject(project_id)

    fun getCountDoneTaskOfProject(project_id: Int): LiveData<Int> =
        task.countDoneOfProject(project_id)

    fun todo(project_id: Int): LiveData<List<Task>> = task.todo(project_id)
    fun doing(project_id: Int): LiveData<List<Task>> = task.doing(project_id)
    fun done(project_id: Int): LiveData<List<Task>> = task.done(project_id)
    fun getTaskById(id: Int): LiveData<Task> = task.one(id)

    fun insertTask(item: Task) = dickIO.execute { task.insert(item) }
    fun updateTask(item: Task) = dickIO.execute { task.update(item) }
    fun deleteTaskById(id: Int) = dickIO.execute { task.deleteById(id) }

    // endregion

    // region TAG --------------

    fun getTagById(id: Int): LiveData<Tag> = tag.one(id)
    fun allTag(): LiveData<List<Tag>> = tag.all()

    fun insertTag(item: Tag) = dickIO.execute { tag.insert(item) }
    fun updateTag(item: Tag) = dickIO.execute { tag.update(item) }
    fun deleteTagById(id: Int) = dickIO.execute { tag.deleteById(id) }

    // endregion

    // region BACKLOG --------------

    fun getBackLogById(id: Int): LiveData<BackLog> = backlog.one(id)
    fun allBackLog(): LiveData<List<BackLog>> = backlog.all()

    fun insertBackLog(item: BackLog) = dickIO.execute { backlog.insert(item) }
    fun updateBackLog(item: BackLog) = dickIO.execute { backlog.update(item) }
    fun deleteBackLogById(id: Int) = dickIO.execute { backlog.deleteById(id) }

    // endregion
}