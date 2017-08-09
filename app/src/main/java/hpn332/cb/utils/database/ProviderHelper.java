package hpn332.cb.utils.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import hpn332.cb.utils.model.TagStructure;
import hpn332.cb.utils.model.TaskStructure;

public class ProviderHelper {

	private static final String TAG = "ProviderHelper";


	public static void queryStepListTask(
			Context context, int steP, ArrayList<TaskStructure> arrayList) {
		Log.d(TAG, "setupListTask: step : " + steP);

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(
						Contract.buildUri(
								Contract.URI_TASK_STEP, String.valueOf(steP)),
						null,
						null,
						null,
						null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int    id    = cursor.getInt(cursor.getColumnIndex(Contract.TaskEntry.ID));
				String title = cursor.getString(cursor.getColumnIndex(Contract.TaskEntry.TITLE));
				String desc = cursor
						.getString(cursor.getColumnIndex(Contract.TaskEntry.DESCRIPTION));
				String tag  = cursor.getString(cursor.getColumnIndex(Contract.TaskEntry.TAGS));
				int    step = cursor.getInt(cursor.getColumnIndex(Contract.TaskEntry.STEP));
				int    rank = cursor.getInt(cursor.getColumnIndex(Contract.TaskEntry.RANK));

				arrayList.add(new TaskStructure(id, title, desc, tag, step, rank));
			}
			cursor.close();
		}
	}

	public static void queryListTag(Context context, ArrayList<TagStructure> arrayList) {
		Log.d(TAG, "queryListTag: setup tags list");

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(Contract.URI_TAG, null, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				int    id    = cursor.getInt(cursor.getColumnIndex(Contract.TagEntry.ID));
				String title = cursor.getString(cursor.getColumnIndex(Contract.TagEntry.TITLE));
				String desc =
						cursor.getString(cursor.getColumnIndex(Contract.TagEntry.DESCRIPTION));
				int color = cursor.getInt(cursor.getColumnIndex(Contract.TagEntry.COLOR));

				arrayList.add(new TagStructure(id, title, desc, color));
			}
			cursor.close();
		}
	}


	public static void insertNewTask(
			Context context, String title, String desc, String tag, int step, int rank) {
		ContentValues values = new ContentValues();

		values.put(Contract.TaskEntry.TITLE, title);
		values.put(Contract.TaskEntry.DESCRIPTION, desc);
		values.put(Contract.TaskEntry.TAGS, tag);
		values.put(Contract.TaskEntry.STEP, step);
		values.put(Contract.TaskEntry.RANK, rank);

		Uri uri = context.getContentResolver().insert(Contract.URI_TASK, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}

	public static void insertNewTag(
			Context context, String title, String desc, int color) {
		ContentValues values = new ContentValues();

		values.put(Contract.TagEntry.TITLE, title);
		values.put(Contract.TagEntry.DESCRIPTION, desc);
		values.put(Contract.TagEntry.COLOR, color);

		Uri uri = context.getContentResolver().insert(Contract.URI_TAG, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}

	public static void updateOneTask(
			Context context, int id, String title, String desc, String tag, int step, int rank) {

		ContentValues values = new ContentValues();

		values.put(Contract.TaskEntry.TITLE, title);
		values.put(Contract.TaskEntry.DESCRIPTION, desc);
		values.put(Contract.TaskEntry.TAGS, tag);
		values.put(Contract.TaskEntry.STEP, step);
		values.put(Contract.TaskEntry.RANK, rank);

		int update = context.getContentResolver()
				.update(
						Contract.buildUri(Contract.URI_TASK, String.valueOf(id)),
						values, null, null);

		Log.d(TAG, "updateOneTask: update: " + update);
	}

	public static void updateOneTag(
			Context context, int id, String title, String desc, int color) {

		ContentValues values = new ContentValues();

		values.put(Contract.TagEntry.TITLE, title);
		values.put(Contract.TagEntry.DESCRIPTION, desc);
		values.put(Contract.TagEntry.COLOR, color);

		int update = context.getContentResolver()
				.update(
						Contract.buildUri(Contract.URI_TAG, String.valueOf(id)),
						values, null, null);

		Log.d(TAG, "updateOneTag: update: " + update);
	}
}
