package hpn332.cb.utils.database;

import android.net.Uri;

class DBContract {

	interface ProjectColumns {
		String ID          = "_id";
		String TITLE       = "title";
		String DESCRIPTION = "description";
	}

	interface TagColumns {
		String ID          = "_id";
		String TITLE       = "title";
		String DESCRIPTION = "description";
		String COLOR       = "color";
	}

	interface BackLogColumns {
		String ID          = "_id";
		String TITLE       = "title";
		String DESCRIPTION = "description";
		String PROJECT     = "project";
		String COLOR       = "color";
	}

	interface TaskColumns {
		String ID          = "_id";
		String TITLE       = "title";
		String DESCRIPTION = "description";
		String PROJECT     = "project";
		String BACKLOG     = "backlog";
		String TAGS        = "tags";
		String STEP        = "step";
		String RANK        = "rank";
	}

	static final String AUTHORITY     = "hpn332.cb";
	static final Uri    BASE_URI      = Uri.parse("content://" + AUTHORITY);
	static final String TABLE_PROJECT = "project";
	static final String TABLE_TASK    = "task";
	static final String TABLE_TAG     = "tag";
	static final String TABLE_BACKLOG = "backlog";

	static final Uri URI_PROJECT = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_PROJECT).build();

	static final Uri URI_TASK = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TASK).build();

	static final Uri URI_TASK_STEP = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TASK + "step").build();

	static final Uri URI_TAG = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_TAG).build();

	static final Uri URI_BACKLOG = BASE_URI.buildUpon()
			.appendEncodedPath(TABLE_BACKLOG).build();

	static class ProjectEntry implements ProjectColumns {

		static final String CREATE_TABLE =
				"CREATE TABLE " + TABLE_PROJECT + " ("
						+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ TITLE + " TEXT,"
						+ DESCRIPTION + " TEXT"
						+ ");";

		static final String CONTENT_TYPE      =
				"vnd.android.cursor.dir/vnd." + AUTHORITY + ".project";
		static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd." + AUTHORITY + ".project";
	}

	static class TaskEntry implements TaskColumns {

		static final String CREATE_TABLE =
				"CREATE TABLE " + TABLE_TASK + " ("
						+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ TITLE + " TEXT,"
						+ DESCRIPTION + " TEXT,"
						+ PROJECT + " INTEGER,"
						+ BACKLOG + " INTEGER,"
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

	static class TagEntry implements TagColumns {

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

	static class BackLogEntry implements BackLogColumns {

		static final String CREATE_TABLE =
				"CREATE TABLE " + TABLE_BACKLOG + " ("
						+ ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ TITLE + " TEXT,"
						+ DESCRIPTION + " TEXT,"
						+ PROJECT + " INTEGER,"
						+ COLOR + "  INTEGER"
						+ ");";

		static final String CONTENT_TYPE      =
				"vnd.android.cursor.dir/vnd." + AUTHORITY + ".backlog";
		static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/vnd." + AUTHORITY + ".backlog";
	}


	static Uri buildUri(Uri URI, String id) {return URI.buildUpon().appendEncodedPath(id).build();}

	static String getPathSegments(Uri uri)  {return uri.getPathSegments().get(1);}
}
