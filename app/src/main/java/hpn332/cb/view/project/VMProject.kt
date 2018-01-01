package hpn332.cb.view.project

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository
import hpn332.cb.model.stucture.Project
import java.util.ArrayList

/**
 * Created by hpn332 on 01/01/2018.
 */
class VMProject : ViewModel() {

    private val repository = DataRepository()

    //    lateinit var L_PROJECT: LiveData<List<Project>>

    fun getAllProjects() = repository.allProject()

    fun insertProjects(items: List<Project>) = repository.insertProject(items)

    fun updateProjects(items: List<Project>) = repository.updateProject(items)

    fun deleteProjects(items: List<Project>) = repository.deleteProject(items)

}