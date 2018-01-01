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
    var color_panel: ColorPanelView? = null

    var backlogId = 0


    fun insertProjects(items: List<Project>) = repository.insertProject(items)

}