package hpn332.cb.view.task

import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository

/**
 * Created by hpn332 on 01/01/2018.
 */
class VMTask : ViewModel() {


    private val repository = DataRepository()

    fun getTodo() = repository.allTask()
    fun getDoing() = repository.allTask()
    fun getDone() = repository.allTask()

    fun onClickNext() {

    }
}