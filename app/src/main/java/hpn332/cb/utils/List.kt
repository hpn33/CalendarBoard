package hpn332.cb.utils

import java.util.ArrayList

import hpn332.cb.model.stucture.BackLogStructure
import hpn332.cb.model.stucture.CheckTagStructure
import hpn332.cb.model.stucture.ProjectStructure
import hpn332.cb.model.stucture.TagStructure
import hpn332.cb.model.stucture.TaskStructure

object List {

	var L_PROJECT = ArrayList<ProjectStructure>()

	var L_TAGS = ArrayList<TagStructure>()

	var L_BACKLOG = ArrayList<BackLogStructure>()

	var L_TODO = ArrayList<TaskStructure>()
	var L_DOING = ArrayList<TaskStructure>()
	var L_DONE = ArrayList<TaskStructure>()

	var L_CHECK = ArrayList<CheckTagStructure>()

	val L_STEP = arrayOf(L_TODO, L_DOING, L_DONE)
}
