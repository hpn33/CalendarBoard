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

	private static final int TASK      = 100;
	private static final int TASK_ID   = 101;
	private static final int TASK_STEP = 110;
	private static final int TAGs      = 200;
	private static final int TAG_ID    = 201;

	private static UriMatcher buildUriMatcher() {

		final UriMatcher matcher   = new UriMatcher(UriMatcher.NO_MATCH);
		String           authority = DBContract.AUTHORITY;

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
		String               id;

		switch (match) {
			case TASK:
				queryBuilder.setTables(DBContract.TABLE_TASK);
				break;
			case TASK_ID:
				queryBuilder.setTables(DBContract.TABLE_TASK);
				id = DBContract.getId(uri);
				queryBuilder.appendWhere(DBContract.TaskEntry.ID + " = " + id);
				break;
			case TASK_STEP:
				queryBuilder.setTables(DBContract.TABLE_TASK);
				String step = DBContract.getId(uri);
				queryBuilder.appendWhere(DBContract.TaskEntry.STEP + " = " + step);
				break;
			case TAGs:
				queryBuilder.setTables(DBContract.TABLE_TAG);
				break;
			case TAG_ID:
				queryBuilder.setTables(DBContract.TABLE_TAG);
				id = DBContract.getId(uri);
				queryBuilder.appendWhere(DBContract.TagEntry.ID + " = " + id);
				break;

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}

		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null,
		                                   sortOrder);

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Nullable
	@Override
	public Uri insert(
			@NonNull Uri uri, @Nullable ContentValues contentValues) {

		final SQLiteDatabase db    = dbHelper.getWritableDatabase();
		final int            match = mUriMatcher.match(uri);
		long                 id;

		switch (match) {
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
		String               id;

		Log.d(TAG, "update: " + match);

		switch (match) {
			case TASK:
				return db.update(DBContract.TABLE_TASK, contentValues, s, strings);
			case TASK_ID:
				id = DBContract.getId(uri);
				selectionCriteria = DBContract.TaskEntry.ID + " = " + id
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.update(DBContract.TABLE_TASK, contentValues, selectionCriteria, strings);

			case TAGs:
				return db.update(DBContract.TABLE_TAG, contentValues, s, strings);
			case TAG_ID:
				id = DBContract.getId(uri);
				selectionCriteria = DBContract.TagEntry.ID + " = " + id
						+ (!TextUtils.isEmpty(s) ? " AND ( " + s + ")" : "");
				return db.update(DBContract.TABLE_TAG, contentValues, selectionCriteria, null);

			default:
				throw new IllegalArgumentException("Unknown Uri: " + uri);
		}
	}

	@Override
	public int delete(
			@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {

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