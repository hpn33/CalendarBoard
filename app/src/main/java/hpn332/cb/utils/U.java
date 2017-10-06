package hpn332.cb.utils;

import java.util.ArrayList;

import hpn332.cb.model.stucture.BackLogStructure;
import hpn332.cb.model.stucture.CheckTagStructure;
import hpn332.cb.model.stucture.ProjectStructure;
import hpn332.cb.model.stucture.TagStructure;
import hpn332.cb.model.stucture.TaskStructure;

public class U {

	public static class Type {

		public static final int ADD_PROJECT = 10;
		public static final int ADD_BACKLOG = 11;
		public static final int ADD_TASK    = 12;
		public static final int ADD_TAG     = 13;

		public static final int EDIT_PROJECT = 20;
		public static final int EDIT_BACKLOG = 21;
		public static final int EDIT_TASK    = 22;
		public static final int EDIT_TAG     = 23;
	}

	public static class Key {

		public static final String PROJECT  = "ADD_PROJECT";
		public static final String POSITION = "POSITION";
		public static final String STEP     = "STEP";
		public static final String UPDATE   = "UPDATE";
		public static final String TYPE     = "TYPE";
		public static final String KIND     = "KIND";
	}

	public static class AList {

		public static ArrayList<ProjectStructure> L_PROJECT = new ArrayList<>();

		public static ArrayList<TagStructure> L_TAGS = new ArrayList<>();

		public static ArrayList<BackLogStructure> L_BACKLOG = new ArrayList<>();

		public static ArrayList<TaskStructure>
				L_TODO = new ArrayList<>(), L_DOING = new ArrayList<>(), L_DONE = new ArrayList<>();

		public static ArrayList<CheckTagStructure> L_CHECK = new ArrayList<>();

		public static final ArrayList[] L_STEP = {L_TODO, L_DOING, L_DONE};

	}
}
