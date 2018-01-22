package hpn332.cb.view.project

import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository

/**
 * Created by hpn332 on 01/01/2018.
 */
class VMProject : ViewModel() {

    init {
    	println("init")
    }

    private val repository = DataRepository()

    fun getAllProject() = repository.allProject()

}