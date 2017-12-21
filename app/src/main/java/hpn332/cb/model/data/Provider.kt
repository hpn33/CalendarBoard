package hpn332.cb.model.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import android.util.Log

class Provider : ContentProvider() {

    private var dbHelper: DBHelper? = null

    private fun deleteDatabase() {
        dbHelper!!.close()
        DBHelper.deleteDatabase(context!!)
        dbHelper = DBHelper(context!!)
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context!!)
        return true
    }

    override fun getType(uri: Uri): String? {
        val match = mUriMatcher.match(uri)
        return when (match) {
            PROJECT    -> DBContract.ProjectEntry.CONTENT_TYPE
            PROJECT_ID -> DBContract.ProjectEntry.CONTENT_ITEM_TYPE
            TASK       -> DBContract.TaskEntry.CONTENT_TYPE
            TASK_ID    -> DBContract.TaskEntry.CONTENT_ITEM_TYPE
            TASK_STEP  -> DBContract.TaskEntry.CONTENT_ITEM_STEP
            TAGs       -> DBContract.TagEntry.CONTENT_TYPE
            TAG_ID     -> DBContract.TagEntry.CONTENT_ITEM_TYPE
            BACKLOG    -> DBContract.BackLogEntry.CONTENT_TYPE
            BACKLOG_ID -> DBContract.BackLogEntry.CONTENT_ITEM_TYPE

            else       -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?): Cursor? {

        val db = dbHelper!!.readableDatabase
        val match = mUriMatcher.match(uri)
        val queryBuilder = SQLiteQueryBuilder()

        when (match) {
            PROJECT    -> queryBuilder.tables = DBContract.TABLE_PROJECT
            PROJECT_ID -> {
                queryBuilder.tables = DBContract.TABLE_PROJECT
                queryBuilder.appendWhere(
                    DBContract.TaskEntry.ID + " = " + DBContract.getPathSegments(uri))
            }
            TASK       -> queryBuilder.tables = DBContract.TABLE_TASK
            TASK_ID    -> {
                queryBuilder.tables = DBContract.TABLE_TASK
                queryBuilder.appendWhere(
                    DBContract.TaskEntry.ID + " = " + DBContract.getPathSegments(uri))
            }
            TASK_STEP  -> {
                queryBuilder.tables = DBContract.TABLE_TASK
                queryBuilder.appendWhere(
                    DBContract.TaskEntry.STEP + " = " + DBContract.getPathSegments(uri)
                        + if (!TextUtils.isEmpty(selection))
                        " AND  $selection "
                    else
                        "")
            }
            TAGs       -> queryBuilder.tables = DBContract.TABLE_TAG
            TAG_ID     -> {
                queryBuilder.tables = DBContract.TABLE_TAG
                queryBuilder.appendWhere(
                    DBContract.TagEntry.ID + " = " + DBContract.getPathSegments(uri))
            }

            BACKLOG    -> queryBuilder.tables = DBContract.TABLE_BACKLOG
            BACKLOG_ID -> {
                queryBuilder.tables = DBContract.TABLE_BACKLOG
                queryBuilder.appendWhere(
                    DBContract.BackLogEntry.ID + " = " + DBContract.getPathSegments(uri))
            }


            else       -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }

        val cursor = queryBuilder.query(
            db, projection, selection, selectionArgs, null, null, sortOrder)

        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {

        val db = dbHelper!!.writableDatabase
        val match = mUriMatcher.match(uri)
        val id: Long

        when (match) {
            PROJECT -> {
                id = db.insertOrThrow(DBContract.TABLE_PROJECT, null, contentValues)
                return DBContract.buildUri(DBContract.URI_PROJECT, id.toString())
            }
            TASK    -> {
                id = db.insertOrThrow(DBContract.TABLE_TASK, null, contentValues)
                return DBContract.buildUri(DBContract.URI_TASK, id.toString())
            }
            TAGs    -> {
                id = db.insertOrThrow(DBContract.TABLE_TAG, null, contentValues)
                return DBContract.buildUri(DBContract.URI_TAG, id.toString())
            }
            BACKLOG -> {
                id = db.insertOrThrow(DBContract.TABLE_BACKLOG, null, contentValues)
                return DBContract.buildUri(DBContract.URI_BACKLOG, id.toString())
            }

            else    -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
    }

    override fun update(
        uri: Uri, contentValues: ContentValues?, s: String?,
        strings: Array<String>?): Int {

        val db = dbHelper!!.writableDatabase
        val match = mUriMatcher.match(uri)
        val selectionCriteria: String

        Log.d(TAG, "update: " + match)

        when (match) {
            PROJECT    -> return db.update(DBContract.TABLE_PROJECT, contentValues, s, strings)
            PROJECT_ID -> {
                selectionCriteria = (DBContract.TaskEntry.ID + " = " + DBContract.getPathSegments(uri)
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.update(
                    DBContract.TABLE_PROJECT, contentValues, selectionCriteria, strings)
            }

            TASK       -> return db.update(DBContract.TABLE_TASK, contentValues, s, strings)
            TASK_ID    -> {
                selectionCriteria = (DBContract.TaskEntry.ID + " = " + DBContract.getPathSegments(uri)
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.update(DBContract.TABLE_TASK, contentValues, selectionCriteria, strings)
            }

            TAGs       -> return db.update(DBContract.TABLE_TAG, contentValues, s, strings)
            TAG_ID     -> {
                selectionCriteria = (DBContract.TagEntry.ID + " = " + DBContract.getPathSegments(uri)
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.update(DBContract.TABLE_TAG, contentValues, selectionCriteria, strings)
            }

            BACKLOG    -> return db.update(DBContract.TABLE_BACKLOG, contentValues, s, strings)
            BACKLOG_ID -> {
                selectionCriteria = (DBContract.BackLogEntry.ID + " = " + DBContract.getPathSegments(uri)
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.update(DBContract.TABLE_BACKLOG,
                    contentValues, selectionCriteria, strings)
            }

            else       -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {

        if (uri == DBContract.BASE_URI) {
            deleteDatabase()
            return 0
        }

        val db = dbHelper!!.writableDatabase
        val match = mUriMatcher.match(uri)
        val id = DBContract.getPathSegments(uri)
        val selectionCriteria: String

        when (match) {

            PROJECT_ID -> {
                selectionCriteria = (DBContract.ProjectEntry.ID + " = " + id
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.delete(DBContract.TABLE_PROJECT, selectionCriteria, strings)
            }

            TASK_ID    -> {
                selectionCriteria = (DBContract.TaskEntry.ID + " = " + id
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.delete(DBContract.TABLE_TASK, selectionCriteria, strings)
            }

            TAG_ID     -> {
                selectionCriteria = (DBContract.TagEntry.ID + " = " + id
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.delete(DBContract.TABLE_TAG, selectionCriteria, strings)
            }

            BACKLOG_ID -> {
                selectionCriteria = (DBContract.BackLogEntry.ID + " = " + id
                    + if (!TextUtils.isEmpty(s)) " AND ( $s)" else "")
                return db.delete(DBContract.TABLE_BACKLOG, selectionCriteria, strings)
            }

            else       -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
    }

    companion object {

        private val TAG = "Provider"

        private val mUriMatcher = buildUriMatcher()

        private val PROJECT = 100
        private val PROJECT_ID = 101
        private val TASK = 200
        private val TASK_ID = 201
        private val TASK_STEP = 210
        private val TAGs = 300
        private val TAG_ID = 301
        private val BACKLOG = 400
        private val BACKLOG_ID = 401

        private fun buildUriMatcher(): UriMatcher {

            val matcher = UriMatcher(UriMatcher.NO_MATCH)
            val authority = DBContract.AUTHORITY

            with(matcher) {
                addURI(authority, DBContract.TABLE_PROJECT, PROJECT)
                addURI(authority, DBContract.TABLE_PROJECT + "/*", PROJECT_ID)
                addURI(authority, DBContract.TABLE_TASK, TASK)
                addURI(authority, DBContract.TABLE_TASK + "/*", TASK_ID)
                addURI(authority, DBContract.TABLE_TASK + "step/*", TASK_STEP)
                addURI(authority, DBContract.TABLE_TAG, TAGs)
                addURI(authority, DBContract.TABLE_TAG + "/*", TAG_ID)
                addURI(authority, DBContract.TABLE_BACKLOG, BACKLOG)
                addURI(authority, DBContract.TABLE_BACKLOG + "/*", BACKLOG_ID)
            }
            return matcher
        }
    }
}