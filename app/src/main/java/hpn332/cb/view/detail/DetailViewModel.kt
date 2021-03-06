package hpn332.cb.view.detail

import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task

/**
 * Created by hpn332 on 01/01/2018.
 */
class DetailViewModel : ViewModel() {

    private val repository = DataRepository()

    var project = Project()
    var backlog = BackLog()
    var tag = Tag()

    fun getTodo() = repository.todo(project.id)
    fun getDoing() = repository.doing(project.id)
    fun getDone() = repository.done(project.id)

    fun updateTask(item: Task) = repository.updateTask(item)

    fun getProjectById(id: Int) = repository.getProjectById(id)
    fun getBackLogById(id: Int) = repository.getBackLogById(id)
    fun getTagById(id: Int) = repository.getTagById(id)
}