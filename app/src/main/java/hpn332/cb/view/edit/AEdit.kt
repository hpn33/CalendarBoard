package hpn332.cb.view.edit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hpn332.cb.utils.Key

/**
 *
 */
abstract class AEdit : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(setView(), container, false)

        init(view, activity.intent.getIntExtra(Key.ID, 0))

        return view
    }

    /**
     * this take layout id
     *
     * @sample R.id.sample
     *
     * @return layout id
     */
    abstract fun setView(): Int

    /**
     * I have everything that need
     * what do you want do?
     *
     * @param view can take element
     * @param id can take data
     */
    abstract fun init(view: View, id: Int)

}