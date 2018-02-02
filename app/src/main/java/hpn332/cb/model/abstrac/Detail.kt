package hpn332.cb.model.abstrac

import android.view.View
import hpn332.cb.R

/**
 * Created by hpn332 on 31/01/2018.
 */
abstract class Detail : Frag() {
    override fun setView(): Int = R.layout.fragment_detail

    abstract override fun init(view: View, id: Int)
}