package hpn332.cb.utils.database;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;

import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class Contract {

	interface TagColumns {
		String ID          = "_id";
		String TITLE       = "title";
		String DESCRIPTION = "description";
		String COLOR       = "color";
	}

	interface TaskColumns {
		String ID          = "_id";
		String TITLE       = "title";
		String DESCRIPTION = "description";
		String TAGS        = "tags";
		String STEP        = "step";
		String RANK        = "rank";
	}

	static final String AUTHORITY  = "hpn332.cb";
	static final Uri    BASE_URI   = Uri.parse("content://" + AUTHORITY);
	static final String TABLE_TASK = "task";
	static final String TABLE_TAG  = "tag";
	static final Uri    URI_TASK   = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TASK).build();

	static final Uri URI_TASK_STEP = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TASK + "step").build();

	static final Uri URI_TAG = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TAG).build();

	static class TaskEntry implements TaskColumns, BaseColumns {

		static final String CREATE_TABLE =
				"CREATE TABLE " + TABLE_TASK + " ("
						+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ TITLE + " TEXT,"
						+ DESCRIPTION + " TEXT,"
						+ TAGS + " TEXT,"
						+ STEP + " INTEGER,"
						+ RANK + " INTEGER"
						+ ");";

		static final String CONTENT_TYPE      =
				"vnd.android.cursor.dir/vnd." + AUTHORITY + ".task";
		static final String CONTENT_ITEM_STEP =
				"vnd.android.cursor.step.dir/vnd." + AUTHORITY + ".task";
		static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd." + AUTHORITY + ".task";
	}

	static class TagEntry implements TagColumns, BaseColumns {

		static final String CREATE_TABLE =
				"CREATE TABLE " + TABLE_TAG + " ("
						+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ TITLE + " TEXT,"
						+ DESCRIPTION + " TEXT,"
						+ COLOR + "  INTEGER"
						+ ");";

		static final String CONTENT_TYPE      =
				"vnd.android.cursor.dir/vnd." + AUTHORITY + ".tag";
		static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd." + AUTHORITY + ".tag";
	}


	static Uri buildUri(Uri URI, String id) {
		return URI.buildUpon().appendEncodedPath(id).build();
	}

	static String getId(Uri uri) {return uri.getPathSegments().get(1);}

	static Boolean hasLastPathSegment(Uri uri) {
		String s = getId(uri);
		return !s.isEmpty();
	}

	public static ArrayList<TaskStructure>
			L_STEP_1 = new ArrayList<>(), L_STEP_2 = new ArrayList<>(),
			L_STEP_3 = new ArrayList<>(), L_STEP_4 = new ArrayList<>();

	public static ArrayList<TagStructure>
			L_TAGS = new ArrayList<>();

	public static final ArrayList[] listStep = {L_STEP_1, L_STEP_2, L_STEP_3, L_STEP_4};
}
