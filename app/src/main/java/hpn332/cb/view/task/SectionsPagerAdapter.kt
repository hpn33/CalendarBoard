package hpn332.cb.view.task

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

	private val fragmentList = ArrayList<FragmentStructure>()


	override fun getItem(position: Int): Fragment = fragmentList[position].fragment

	override fun getCount(): Int = fragmentList.size

	override fun getPageTitle(position: Int): CharSequence = fragmentList[position].title


    fun addFragment(fragment: Fragment, title: String) =
        fragmentList.add(FragmentStructure(fragment, title))

}