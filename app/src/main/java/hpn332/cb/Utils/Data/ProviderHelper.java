package hpn332.cb.Utils.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import hpn332.cb.Utils.Structure;

public class ProviderHelper {

	private static final String TAG = "ProviderHelper";

	public static void queryListTask(Context context, int steP, ArrayList<Structure> arrayList) {
		Log.d(TAG, "setupListTask: step : " + steP);

		if (!arrayList.isEmpty()) arrayList.clear();

		Cursor cursor = context.getContentResolver()
				.query(Contract.URI_TASK,
				       null,
				       null,
				       new String[]{String.valueOf(steP)},
				       null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				String title = cursor.getString(cursor.getColumnIndex(Contract.TaskEntry.TITLE));
				String desc = cursor
						.getString(cursor.getColumnIndex(Contract.TaskEntry.DESCRIPTION));
				String tag  = cursor.getString(cursor.getColumnIndex(Contract.TaskEntry.TAGS));
				int    step = cursor.getInt(cursor.getColumnIndex(Contract.TaskEntry.STEP));
				int    rank = cursor.getInt(cursor.getColumnIndex(Contract.TaskEntry.RANK));

				Structure structure = new Structure(title, desc, tag, step, rank);
				arrayList.add(structure);
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
			Context context, String title, String desc, String color) {
		ContentValues values = new ContentValues();

		values.put(Contract.TagEntry.TITLE, title);
		values.put(Contract.TagEntry.DESCRIPTION, desc);
		values.put(Contract.TagEntry.COLOR, color);

		Uri uri = context.getContentResolver().insert(Contract.URI_TAG, values);

		if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri);
	}
}
