package hpn332.cb.utils.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

public class Provider extends ContentProvider {

	private static final String TAG = "Provider";

	private DBHelper dbHelper;

	private static final UriMatcher mUriMatcher = buildUriMatcher();

	private static final int PROJECT    = 100;
	private static final int PROJECT_ID = 101;
	private static final int TASK       = 200;
	private static final int TASK_ID    = 201;
	private static final int TASK_STEP  = 210;
	private static final int TAGs       = 300;
	private static final int TAG_ID     = 301;

	private static UriMatcher buildUriMatcher() {

		final UriMatcher matcher   = new UriMatcher(UriMatcher.NO_MATCH);
		String           authority = DBContract.AUTHORITY;

		matcher.addURI(authority, DBContract.TABLE_PROJECT, PROJECT);
		matcher.addURI(authority, DBContract.TABLE_PROJECT + "/*", PROJECT_ID);
		matcher.addURI(authority, DBContract.TABLE_TASK, TASK);
		matcher.addURI(authority, DBContract.TABLE_TASK + "/*", TASK_ID);
		matcher.addURI(authority, DBContract.TABLE_TASK + "step/*", TASK_STEP);
		matcher.addURI(authority, DBContract.TABLE_TAG, TAGs);
		matcher.addURI(authority, DBContract.TABLE_TAG + "/*", TAG_ID);

		return matcher;
	}

	private void deleteDatabase() {
		dbHelper.close();
		DBHelper.deleteDatabase(getContext());
		dbHelper = new DBHelper(getContext());
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public String getType(@NonNull Uri uri) {
		final int match = mUriMatcher.match(uri);
		switch (match) {
			case PROJECT:
				return DBContract.ProjectEntry.CONTENT_TYPE;
			case PROJECT_ID:
				return DBContract.ProjectEntry.CONTENT_ITEM_TYPE;
			case TASK:
				return DBContract.TaskEntry.CONTENT_TYPE;
			case TASK_ID:
				return DBContract.TaskEntry.CONTENT_ITEM_TYPE;
			case TASK_STEP:
				return DBContract.TaskEntry.CONTENT_ITEM_STEP;
			case TAGs:
				return DBContract.TagEntry.CONTENT_TYPE;
			case TAG_ID:
				return DBContract.TagEntry.CONTENT_ITEM_TYPE;

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Nullable
	@Override
	public Cursor query(
			@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
			@Nullable String[] selectionArgs, @Nullable String sortOrder) {

		final SQLiteDatabase db           = dbHelper.getReadableDatabase();
		final int            match        = mUriMatcher.match(uri);
		SQLiteQueryBuilder   queryBuilder = new SQLiteQueryBuilder();

		switch (match) {
			case PROJECT:
				queryBuilder.setTables(DBContract.TABLE_PROJECT);
				break;
			case PROJECT_ID:
				queryBuilder.setTables(DBContract.TABLE_PROJECT);
				queryBuilder.appendWhere(DBContract.TaskEntry.ID + " = " + DBContract.getId(uri));
				break;
			case TASK:
				queryBuilder.setTables(DBContract.TABLE_TASK);
				break;
			case TASK_ID:
				queryBuilder.setTables(DBContract.TABLE_TASK);
				queryBuilder.appendWhere(DBContract.TaskEntry.ID + " = " + DBContract.getId(uri));
				break;
			case TASK_STEP:
				queryBuilder.setTables(DBContract.TABLE_TASK);
				queryBuilder.appendWhere(
						DBContract.TaskEntry.STEP + " = " + DBContract.getId(uri)
								+ (!TextUtils.isEmpty(selection) ? " AND  " + selection + " "
								                                 : ""));
				break;
			case TAGs:
				queryBuilder.setTables(DBContract.TABLE_TAG);
				break;
			case TAG_ID:
				queryBuilder.setTables(DBContract.TABLE_TAG);
				queryBuilder.appendWhere(DBContract.TagEntry.ID + " = " + DBContract.getId(uri));
				break;

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}

		Cursor cursor = queryBuilder.query(
				db, projection, selection, selectionArgs, null, null, sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

		final SQLiteDatabase db    = dbHelper.getWritableDatabase();
		final int            match = mUriMatcher.match(uri);
		long                 id;

		switch (match) {
			case PROJECT:
				id = db.insertOrThrow(DBContract.TABLE_PROJECT, null, contentValues);
				return DBContract.buildUri(DBContract.URI_PROJECT, String.valueOf(id));
			case TASK:
				id = db.insertOrThrow(DBContract.TABLE_TASK, null, contentValues);
				return DBContract.buildUri(DBContract.URI_TASK, String.valueOf(id));
			case TAGs:
				id = db.insertOrThrow(DBContract.TABLE_TAG, null, contentValues);
				return DBContract.buildUri(DBContract.URI_TAG, String.valueOf(id));

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int update(
			@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
			@Nullable String[] strings) {

		final SQLiteDatabase db    = dbHelper.getWritableDatabase();
		final int            match = mUriMatcher.match(uri);
		String               selectionCriteria;

		Log.d(TAG, "update: " + match);

		switch (match) {
			case PROJECT:
				return db.update(DBContract.TABLE_PROJECT, contentValues, s, strings);
			case PROJECT_ID:
				selectionCriteria = DBContract.TaskEntry.ID + " = " + DBContract.getId(uri)
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.update(
						DBContract.TABLE_PROJECT, contentValues, selectionCriteria, strings);

			case TASK:
				return db.update(DBContract.TABLE_TASK, contentValues, s, strings);
			case TASK_ID:
				selectionCriteria = DBContract.TaskEntry.ID + " = " + DBContract.getId(uri)
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.update(DBContract.TABLE_TASK, contentValues, selectionCriteria, strings);

			case TAGs:
				return db.update(DBContract.TABLE_TAG, contentValues, s, strings);
			case TAG_ID:
				selectionCriteria = DBContract.TagEntry.ID + " = " + DBContract.getId(uri)
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.update(DBContract.TABLE_TAG, contentValues, selectionCriteria, strings);

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

		if (uri.equals(DBContract.BASE_URI)) {
			deleteDatabase();
			return 0;
		}

		final SQLiteDatabase db    = dbHelper.getWritableDatabase();
		final int            match = mUriMatcher.match(uri);
		String               id    = DBContract.getId(uri);
		String               selectionCriteria;

		switch (match) {
			case TASK_ID:
				selectionCriteria = DBContract.TaskEntry.ID + " = " + id
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.delete(DBContract.TABLE_TASK, selectionCriteria, strings);

			case TAG_ID:
				selectionCriteria = DBContract.TagEntry.ID + " = " + id
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.delete(DBContract.TABLE_TAG, selectionCriteria, strings);

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}
}