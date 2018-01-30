package hpn332.cb.view.project

import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Task

/**
 * Created by hpn332 on 01/01/2018.
 */
class ProjectViewModel : ViewModel() {

    private val repository = DataRepository()

    var project= Project()

    fun getTodo() = repository.todo(project.id)
    fun getDoing() = repository.doing(project.id)
    fun getDone() = repository.done(project.id)

    fun updateTask(item: Task) = repository.updateTask(item)
    //    fun updateStepTask(id: Int, step: Int) = repository.updateStepTask(id, step)

    fun getProject() = repository.getProjectById(project.id)
    fun deleteProjectById(id: Int) = repository.deleteProjectById(id)
}