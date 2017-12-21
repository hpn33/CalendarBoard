package hpn332.cb.utils

import java.util.ArrayList

import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.CheckTagStructure
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task

object List {

	var L_PROJECT = ArrayList<Project>()

	var L_TAGS = ArrayList<Tag>()

	var L_BACKLOG = ArrayList<BackLog>()

	var L_TODO = ArrayList<Task>()
	var L_DOING = ArrayList<Task>()
	var L_DONE = ArrayList<Task>()

	var L_CHECK = ArrayList<CheckTagStructure>()

	val L_STEP = arrayOf(L_TODO, L_DOING, L_DONE)
}
