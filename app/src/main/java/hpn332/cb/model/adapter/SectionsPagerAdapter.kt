package hpn332.cb.model.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import hpn332.cb.R
import kotlinx.android.synthetic.main.custom_tab.view.*
import java.util.*


class SectionsPagerAdapter(private var context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragmentList = ArrayList<FragmentHolder>()


    override fun getItem(position: Int): Fragment = fragmentList[position].fragment

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence = fragmentList[position].title


    data class FragmentHolder(val fragment: Fragment,
                              val title: String = "",
                              var count: Int = 0)

    @SuppressLint("InflateParams")
    private fun getTabView(position: Int = 0,
                           titleS: String = fragmentList[position].title,
                           countS: Int = fragmentList[position].count): View {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null)

        with(view) {
            title.text = titleS
            count.text = "$countS"
        }

        return view
    }

    fun addFragment(fragment: Fragment, title: String = "") =
        fragmentList.add(FragmentHolder(fragment, title))

    private lateinit var tab: TabLayout

    fun setCustomTab(tabs: TabLayout = tab) {
        tab = tabs
        for (i in 0 until fragmentList.size)
            tabs.getTabAt(i)?.customView = getTabView(i)
    }

    fun setCount(position: Int, count: Int) {
        fragmentList[position].count = count
        setCustomTab()
    }
}