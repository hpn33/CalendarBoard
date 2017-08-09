package hpn332.cb.utils.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";

	private static final String DATABASE_NAME    = "db_calender.db";
	private static final int    DATABASE_VERSION = 1;

	DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(Contract.TaskEntry.CREATE_TABLE);

		db.execSQL(Contract.TagEntry.CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data");

		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_TASK);
			db.execSQL("DROP TABLE IF EXISTS " + Contract.TABLE_TAG);

			onCreate(db);
		}
	}

	static void deleteDatabase(Context context) {
		context.deleteDatabase(DATABASE_NAME);
	}
}