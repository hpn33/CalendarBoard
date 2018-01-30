package hpn332.cb.view.edit

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task
import hpn332.library.view.ColorPanelView

/**
 * Created by hpn332 on 31/12/2017.
 */
class EditViewModel : ViewModel() {

    private val repository = DataRepository()

    @SuppressLint("StaticFieldLeak")
    var colorPanelView: ColorPanelView? = null

    var backlogId = 0

    fun getProjectById(id: Int) = repository.getProjectById(id)
    fun insertProject(item: Project) = repository.insertProject(item)
    fun updateProject(item: Project) = repository.updateProject(item)
    fun deleteProjectById(id: Int) = repository.deleteProjectById(id)

    fun getTaskById(id: Int) = repository.getTaskById(id)
    fun insertTask(item: Task) = repository.insertTask(item)
    fun updateTask(item: Task) = repository.updateTask(item)
    fun deleteTasksById(id: Int) = repository.deleteTaskById(id)

    fun getTagById(id: Int) = repository.getTagById(id)
    fun insertTag(item: Tag) = repository.insertTag(item)
    fun updateTag(item: Tag) = repository.updateTag(item)
    fun deleteTagById(id: Int) = repository.deleteTagById(id)

    fun getBackLogById(id: Int) = repository.getBackLogById(id)
    fun insertBackLog(item: BackLog) = repository.insertBackLog(item)
    fun updateBackLog(item: BackLog) = repository.updateBackLog(item)
    fun deleteBackLogById(id: Int) = repository.deleteBackLogById(id)
}