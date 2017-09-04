package hpn332.cb.utils;

import java.util.ArrayList;

import hpn332.cb.utils.model.BackLogStructure;
import hpn332.cb.utils.model.CheckTagStructure;
import hpn332.cb.utils.model.ProjectStructure;
import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class AList {

	public static ArrayList<ProjectStructure> L_PROJECT = new ArrayList<>();

	public static ArrayList<TagStructure> L_TAGS = new ArrayList<>();

	public static ArrayList<BackLogStructure> L_BACKLOG = new ArrayList<>();

	public static ArrayList<TaskStructure>
			L_TODO = new ArrayList<>(), L_DOING = new ArrayList<>(), L_DONE = new ArrayList<>();


	public static ArrayList<CheckTagStructure> L_CHECK = new ArrayList<>();


	public static final ArrayList[] L_STEP = { L_TODO, L_DOING, L_DONE};

}
