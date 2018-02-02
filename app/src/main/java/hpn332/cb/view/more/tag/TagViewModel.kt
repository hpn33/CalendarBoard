package hpn332.cb.view.more.tag

import android.arch.lifecycle.ViewModel
import hpn332.cb.model.data.DataRepository

/**
 * Created by hpn332 on 01/01/2018.
 */
class TagViewModel : ViewModel() {

    private val repository = DataRepository()

    fun getAllTag() = repository.allTag()
}