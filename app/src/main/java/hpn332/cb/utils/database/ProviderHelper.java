package hpn332.cb.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class ProviderHelper {

	private static final String TAG = "ProviderHelper";


	public static void queryListTaskByStep(
			Context context, int steP, java.util.ArrayList arrayList) {
		Log.d(TAG, "setupListTask: step : " + steP);

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(
						DBContract.buildUri(
								DBContract.URI_TASK_STEP, String.valueOf(steP)),
						null,
						null,
						null,
						null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int    id    = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.ID));
				String title = cursor.getString(cursor.getColumnIndex(DBContract.TaskEntry.TITLE));
				String desc = cursor
						.getString(cursor.getColumnIndex(DBContract.TaskEntry.DESCRIPTION));
				String tag  = cursor.getString(cursor.getColumnIndex(DBContract.TaskEntry.TAGS));
				int    step = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.STEP));
				int    rank = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.RANK));

				arrayList.add(new TaskStructure(id, title, desc, tag, step, rank));
			}
			cursor.close();
		}
	}

	public static void queryListTag(Context context, java.util.ArrayList arrayList) {
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


	public static void insertNewTask(
			Context context, String title, String desc, String tag, int step, int rank) {
		ContentValues values = new ContentValues();

		values.put(DBContract.TaskEntry.TITLE, title);
		values.put(DBContract.TaskEntry.DESCRIPTION, desc);
		values.put(DBContract.TaskEntry.TAGS, tag);
		values.put(DBContract.TaskEntry.STEP, step);
		values.put(DBContract.TaskEntry.RANK, rank);

		Uri uri = context.getContentResolver().insert(DBContract.URI_TASK, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}

	public static void insertNewTag(
			Context context, String title, String desc, int color) {
		ContentValues values = new ContentValues();

		values.put(DBContract.TagEntry.TITLE, title);
		values.put(DBContract.TagEntry.DESCRIPTION, desc);
		values.put(DBContract.TagEntry.COLOR, color);

		Uri uri = context.getContentResolver().insert(DBContract.URI_TAG, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}

	public static void updateOneTask(
			Context context, int id, String title, String desc, String tag, int step, int rank) {

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

	public static void deleteOneTask(Context context, int id) {

		int delete = context.getContentResolver().
				delete(DBContract.buildUri(DBContract.URI_TASK, String.valueOf(id)),
				       null, null);

		Log.d(TAG, "deleteOneTask: delete task id :: " + delete);
	}

	public static void deleteOneTag(Context context, int id) {
		int delete = context.getContentResolver().
				delete(DBContract.buildUri(DBContract.URI_TAG, String.valueOf(id)),
				       null, null);

		Log.d(TAG, "deleteOneTag: delete tag id :: " + delete);
	}
}
