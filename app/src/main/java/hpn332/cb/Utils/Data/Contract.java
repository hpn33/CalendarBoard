package hpn332.cb.Utils.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;

import hpn332.cb.Utils.TagStructure;
import hpn332.cb.Utils.TaskStructure;

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

	static final        String AUTHORITY  = "hpn332.cb";
	static final        Uri    BASE_URI   = Uri.parse("content://" + AUTHORITY);
	static final        String TABLE_TASK = "task";
	static final        String TABLE_TAG  = "tag";
	public static final Uri    URI_TASK   = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TASK).build();
	public static final Uri    URI_TAG    = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TAG).build();

	static class TaskEntry implements TaskColumns, BaseColumns {
		static final String CONTENT_TYPE      =
				"vnd.android.cursor.dir/vnd." + AUTHORITY + ".task";
		static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd." + AUTHORITY + ".task";
	}

	static class TagEntry implements TagColumns, BaseColumns {
		static final String CONTENT_TYPE      =
				"vnd.android.cursor.dir/vnd." + AUTHORITY + ".tag";
		static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd." + AUTHORITY + ".tag";
	}


	static Uri buildUri(String id) {
		return BASE_URI.buildUpon().appendEncodedPath(id).build();
	}

	static String getId(Uri uri) {
		return uri.getPathSegments().get(1);
	}

	public static ArrayList<TaskStructure>
			L_STEP_1 = new ArrayList<>(), L_STEP_2 = new ArrayList<>(),
			L_STEP_3 = new ArrayList<>(), L_STEP_4 = new ArrayList<>();

	public static ArrayList<TagStructure>
			L_TAGS = new ArrayList<>();

	public static final ArrayList[] listStep = {L_STEP_1, L_STEP_2, L_STEP_3, L_STEP_4};
}
