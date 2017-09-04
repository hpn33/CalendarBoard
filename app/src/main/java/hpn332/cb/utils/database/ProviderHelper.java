package hpn332.cb.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import hpn332.cb.utils.model.BackLogStructure;
import hpn332.cb.utils.model.ProjectStructure;
import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class ProviderHelper {

	private static final String TAG = "ProviderHelper";


	public static void queryListTaskByStep(
			Context context, int steP, int project, ArrayList<TaskStructure> arrayList) {
		Log.d(TAG, "queryListTaskByStep: step : " + steP + " AND Project :: " + project);

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(
						DBContract.buildUri(DBContract.URI_TASK_STEP, String.valueOf(steP)),
						null, DBContract.TaskEntry.PROJECT + " = " + String.valueOf(project),
						null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int    id    = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.ID));
				String title = cursor.getString(cursor.getColumnIndex(DBContract.TaskEntry.TITLE));
				String desc = cursor
						.getString(cursor.getColumnIndex(DBContract.TaskEntry.DESCRIPTION));
				String tag  = cursor.getString(cursor.getColumnIndex(DBContract.TaskEntry.TAGS));
				int    step = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.STEP));
				int    rank = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.RANK));

				arrayList.add(new TaskStructure(id, title, desc, project, tag, step, rank));
			}
			cursor.close();
		}
	}

	public static void queryListBacklogByProject(
			Context context, int project, ArrayList<BackLogStructure> arrayList) {
		Log.d(TAG, "queryListBacklogByProject: Project :: " + project);

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(
						DBContract.URI_BACKLOG, null,
						DBContract.BackLogEntry.PROJECT + " = " + String.valueOf(project),
						null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor.getColumnIndex(DBContract.BackLogEntry.ID));
				String title =
						cursor.getString(cursor.getColumnIndex(DBContract.BackLogEntry.TITLE));
				String desc = cursor
						.getString(cursor.getColumnIndex(DBContract.BackLogEntry.DESCRIPTION));
				int color = cursor.getInt(cursor.getColumnIndex(DBContract.BackLogEntry.COLOR));

				arrayList.add(new BackLogStructure(id, title, desc, project, color));

				Log.d(TAG, "queryListBacklogByProject: title :: " + title + " di :: " + id);
			}
			cursor.close();
		}
	}

	public static void queryListTag(Context context, ArrayList<TagStructure> arrayList) {
		Log.d(TAG, "queryListTag: setup tags list");

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(DBContract.URI_TAG, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int    id    = cursor.getInt(cursor.getColumnIndex(DBContract.TagEntry.ID));
				String title = cursor.getString(cursor.getColumnIndex(DBContract.TagEntry.TITLE));
				String desc =
						cursor.getString(cursor.getColumnIndex(DBContract.TagEntry.DESCRIPTION));
				int color = cursor.getInt(cursor.getColumnIndex(DBContract.TagEntry.COLOR));

				arrayList.add(new TagStructure(id, title, desc, color));
			}
			cursor.close();
		}
	}

	public static void queryListProject(Context context, ArrayList<ProjectStructure> arrayList) {
		Log.d(TAG, "queryListProject: setup tags list");

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(DBContract.URI_PROJECT, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int id =
						cursor.getInt(cursor.getColumnIndex(DBContract.ProjectEntry.ID));
				String title =
						cursor.getString(cursor.getColumnIndex(DBContract.ProjectEntry.TITLE));
				String desc =
						cursor.getString
								(cursor.getColumnIndex(DBContract.ProjectEntry.DESCRIPTION));

				arrayList.add(new ProjectStructure(id, title, desc));
			}
			cursor.close();
		}
	}

	public static void insertNewProject(
			Context context, String title, String desc) {

		Log.d(TAG, "insertNewProject: ");
		ContentValues values = new ContentValues();

		values.put(DBContract.TaskEntry.TITLE, title);
		values.put(DBContract.TaskEntry.DESCRIPTION, desc);

		Uri uri = context.getContentResolver().insert(DBContract.URI_PROJECT, values);

		if (uri != null) Log.d(TAG, "insertNewProject: uri: " + uri);
	}

	public static void insertNewTask(
			Context context, int project, String title,
			String desc, String tag, int step, int rank) {

		Log.d(TAG, "insertNewTask: ");

		ContentValues values = new ContentValues();

		values.put(DBContract.TaskEntry.TITLE, title);
		values.put(DBContract.TaskEntry.DESCRIPTION, desc);
		values.put(DBContract.TaskEntry.PROJECT, project);
		values.put(DBContract.TaskEntry.TAGS, tag);
		values.put(DBContract.TaskEntry.STEP, step);
		values.put(DBContract.TaskEntry.RANK, rank);

		Uri uri = context.getContentResolver().insert(DBContract.URI_TASK, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}

	public static void insertNewTag(Context context, String title, String desc, int color) {

		Log.d(TAG, "insertNewTag: ");

		ContentValues values = new ContentValues();

		values.put(DBContract.TagEntry.TITLE, title);
		values.put(DBContract.TagEntry.DESCRIPTION, desc);
		values.put(DBContract.TagEntry.COLOR, color);

		Uri uri = context.getContentResolver().insert(DBContract.URI_TAG, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}

	public static void insertNewBacklog(
			Context context, int project, String title, String desc, int color) {

		Log.d(TAG, "insertNewBacklog: pro :: " + project + " title :: " + title);

		ContentValues values = new ContentValues();

		values.put(DBContract.BackLogEntry.TITLE, title);
		values.put(DBContract.BackLogEntry.DESCRIPTION, desc);
		values.put(DBContract.BackLogEntry.PROJECT, project);
		values.put(DBContract.BackLogEntry.COLOR, color);

		Uri uri = context.getContentResolver().insert(DBContract.URI_BACKLOG, values);

		if (uri != null) Log.d(TAG, "insertNewBacklog: uri: " + uri);
	}

	public static void updateOneProject(Context context, int id, String title, String desc) {

		Log.d(TAG, "updateOneProject: ");

		ContentValues values = new ContentValues();

		values.put(DBContract.TaskEntry.TITLE, title);
		values.put(DBContract.TaskEntry.DESCRIPTION, desc);

		int update = context.getContentResolver()
				.update(DBContract.buildUri(DBContract.URI_PROJECT, String.valueOf(id)),
				        values, null, null);

		Log.d(TAG, "updateOneProject: update: " + update);
	}

	public static void updateOneTask(
			Context context, int id, String title, String desc, String tag, int step, int rank) {

		Log.d(TAG, "updateOneTask: ");

		ContentValues values = new ContentValues();

		values.put(DBContract.TaskEntry.TITLE, title);
		values.put(DBContract.TaskEntry.DESCRIPTION, desc);
		values.put(DBContract.TaskEntry.TAGS, tag);
		values.put(DBContract.TaskEntry.STEP, step);
		values.put(DBContract.TaskEntry.RANK, rank);

		int update = context.getContentResolver()
				.update(DBContract.buildUri(DBContract.URI_TASK, String.valueOf(id)),
				        values, null, null);

		Log.d(TAG, "updateOneTask: update: " + update);
	}

	public static void updateOneTag(
			Context context, int id, String title, String desc, int color) {

		Log.d(TAG, "updateOneTag: ");

		ContentValues values = new ContentValues();

		values.put(DBContract.TagEntry.TITLE, title);
		values.put(DBContract.TagEntry.DESCRIPTION, desc);
		values.put(DBContract.TagEntry.COLOR, color);

		int update = context.getContentResolver()
				.update(
						DBContract.buildUri(DBContract.URI_TAG, String.valueOf(id)),
						values, null, null);

		Log.d(TAG, "updateOneTag: update: " + update);
	}

	public static void updateOneBacklog(
			Context context, int id, String title, String desc, int color) {

		Log.d(TAG, "updateOneTag: start");

		ContentValues values = new ContentValues();

		values.put(DBContract.BackLogEntry.TITLE, title);
		values.put(DBContract.BackLogEntry.DESCRIPTION, desc);
		values.put(DBContract.BackLogEntry.COLOR, color);

		int update = context.getContentResolver()
				.update(
						DBContract.buildUri(DBContract.URI_BACKLOG, String.valueOf(id)),
						values, null, null);

		Log.d(TAG, "updateOneBacklog: end update: " + update);
	}

	public static void deleteOneProject(Context context, int id) {

		Log.d(TAG, "deleteOneProject: ");

		int delete = context.getContentResolver().
				delete(DBContract.buildUri(DBContract.URI_PROJECT, String.valueOf(id)),
				       null, null);

		Log.d(TAG, "deleteOneProject: delete project id :: " + delete);
	}

	public static void deleteOneTask(Context context, int id) {

		Log.d(TAG, "deleteOneTask: ");

		int delete = context.getContentResolver().
				delete(DBContract.buildUri(DBContract.URI_TASK, String.valueOf(id)),
				       null, null);

		Log.d(TAG, "deleteOneTask: delete task id :: " + delete);
	}

	public static void deleteOneTag(Context context, int id) {

		Log.d(TAG, "deleteOneTag: ");

		int delete = context.getContentResolver().
				delete(DBContract.buildUri(DBContract.URI_TAG, String.valueOf(id)),
				       null, null);

		Log.d(TAG, "deleteOneTag: delete tag id :: " + delete);
	}

	public static void deleteOneBacklog(Context context, int id) {

		Log.d(TAG, "deleteOneBacklog: ");

		int delete = context.getContentResolver().
				delete(DBContract.buildUri(DBContract.URI_BACKLOG, String.valueOf(id)),
				       null, null);

		Log.d(TAG, "deleteOneBacklog: delete backlog id :: " + delete);
	}
}
