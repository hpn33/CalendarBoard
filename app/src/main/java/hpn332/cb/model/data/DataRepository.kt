package hpn332.cb.model.data

import android.arch.lifecycle.LiveData
import hpn332.cb.App.Companion.db
import hpn332.cb.AppExe.dickIO
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task

/**
 * Created by hpn332 on 31/12/2017.
 */
class DataRepository {

    private val project = db.projectDao()
    private val task = db.taskDao()
    private val tag = db.tagDao()
    private val backlog = db.backlogDao()

    fun allProject()/*: LiveData<List<Project>>*/ = project.all()
    fun insertProject(items: List<Project>) = dickIO.execute { project.insert(items) }
    fun updateProject(items: List<Project>) = dickIO.execute { project.update(items) }
    fun deleteProject(items: List<Project>) = dickIO.execute { project.delete(items) }


    fun allTask(): LiveData<List<Task>> = task.all()
    fun insertTask(items: List<Task>) = dickIO.execute { task.insert(items) }
    fun updateTask(items: List<Task>) = dickIO.execute { task.update(items) }
    fun deleteTask(items: List<Task>) = dickIO.execute { task.delete(items) }


    fun allTag(): LiveData<List<Tag>> = tag.all()
    fun insertTag(items: List<Tag>) = dickIO.execute { tag.insert(items) }
    fun updateTag(items: List<Tag>) = dickIO.execute { tag.update(items) }
    fun deleteTag(items: List<Tag>) = dickIO.execute { tag.delete(items) }


    fun allBacklog(): LiveData<List<BackLog>> = backlog.all()
    fun insertBacklog(items: List<BackLog>) = dickIO.execute { backlog.insert(items) }
    fun updateBacklog(items: List<BackLog>) = dickIO.execute { backlog.update(items) }
    fun deleteBacklog(items: List<BackLog>) = dickIO.execute { backlog.delete(items) }
}