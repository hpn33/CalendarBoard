package hpn332.cb.utils;

import java.util.ArrayList;

import hpn332.cb.utils.model.CheckTagStructure;
import hpn332.cb.utils.model.ProjectStructure;
import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class AList {

	public static ArrayList<ProjectStructure> L_PROJECT = new ArrayList<>();

	public static ArrayList<TaskStructure>
			L_STEP_1 = new ArrayList<>(), L_STEP_2 = new ArrayList<>(),
			L_STEP_3 = new ArrayList<>(), L_STEP_4 = new ArrayList<>();

	public static ArrayList<TagStructure> L_TAGS = new ArrayList<>();

	public static ArrayList<CheckTagStructure> L_CHECK = new ArrayList<>();

	public static final ArrayList[] listStep = {L_STEP_1, L_STEP_2, L_STEP_3, L_STEP_4};

}
