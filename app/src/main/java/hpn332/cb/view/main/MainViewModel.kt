package hpn332.cb.view.main

import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository

/**
 * Created by hpn332 on 01/01/2018.
 */
class MainViewModel : ViewModel() {

    private val repository = DataRepository()

    fun getAllProject() = repository.allProject()
    fun getAllBackLog() = repository.allBackLog()
    fun getAllTag() = repository.allTag()

    fun getCountTaskOfProject(project_id: Int) = repository.getCountTaskOfProject(project_id)
    fun getCountDoneTaskOfProject(project_id: Int) = repository.getCountDoneTaskOfProject(project_id)

}