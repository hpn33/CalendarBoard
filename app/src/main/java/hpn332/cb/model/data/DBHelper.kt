package hpn332.cb.model.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

internal class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

	override fun onCreate(db: SQLiteDatabase) {

		db.execSQL(DBContract.ProjectEntry.CREATE_TABLE)
		db.execSQL(DBContract.TaskEntry.CREATE_TABLE)
		db.execSQL(DBContract.TagEntry.CREATE_TABLE)
		db.execSQL(DBContract.BackLogEntry.CREATE_TABLE)
	}

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

		Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
				+ newVersion + ", which will destroy all old data")

		if (oldVersion < newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DBContract.TABLE_PROJECT)
			db.execSQL("DROP TABLE IF EXISTS " + DBContract.TABLE_TASK)
			db.execSQL("DROP TABLE IF EXISTS " + DBContract.TABLE_TAG)
			db.execSQL("DROP TABLE IF EXISTS " + DBContract.TABLE_BACKLOG)

			onCreate(db)
		}
	}

	companion object {

		private val TAG = "DBHelper"

		private val DATABASE_NAME = "db_calender.db"
		private val DATABASE_VERSION = 1

		fun deleteDatabase(context: Context) {
			context.deleteDatabase(DATABASE_NAME)
		}
	}
}
