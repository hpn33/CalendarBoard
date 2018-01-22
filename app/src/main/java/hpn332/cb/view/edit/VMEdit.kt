package hpn332.cb.view.edit

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository
import hpn332.cb.model.stucture.Project
import hpn332.library.view.ColorPanelView

/**
 * Created by hpn332 on 31/12/2017.
 */
class VMEdit : ViewModel() {

    private val repository = DataRepository()

    @SuppressLint("StaticFieldLeak")
    var colorPanelView: ColorPanelView? = null

    var backlogId = 0

//    fun getProjectById(id: Int) = repository.getProjectById(id)

    fun insertProjects(items: List<Project>) = repository.insertProject(items)

    fun updateProjects(items: List<Project>) = repository.updateProject(items)

    fun deleteProjects(items: List<Project>) = repository.deleteProject(items)
}