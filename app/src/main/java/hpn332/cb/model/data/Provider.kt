package hpn332.cb.model.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.deleteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import hpn332.cb.App.Companion.db

@Deprecated("i don`t need now")
class Provider : ContentProvider() {

    override fun onCreate(): Boolean {
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
            null, projection, selection, selectionArgs, null, null, sortOrder)

        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {

        val match = mUriMatcher.match(uri)
        val id: Long

        when (match) {
            PROJECT -> {
            }
            TASK    -> {
            }
            TAGs    -> {
            }
            BACKLOG -> {
            }

            else    -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
        return uri
    }

    override fun update(
        uri: Uri, contentValues: ContentValues?, s: String?,
        strings: Array<String>?): Int {

        val match = mUriMatcher.match(uri)
        val selectionCriteria: String

        Log.d(TAG, "update: " + match)

        when (match) {
            PROJECT    -> {
            }
            PROJECT_ID -> {
            }

            TASK       -> {
            }
            TASK_ID    -> {
            }

            TAGs       -> {
            }
            TAG_ID     -> {
            }

            BACKLOG    -> {
            }
            BACKLOG_ID -> {
            }


            else       -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
        return 0
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {


        val match = mUriMatcher.match(uri)
        val id = DBContract.getPathSegments(uri)
        val selectionCriteria: String

        when (match) {

            PROJECT_ID -> {
            }

            TASK_ID    -> {
            }

            TAG_ID     -> {
            }

            BACKLOG_ID -> {
            }

            else       -> throw IllegalArgumentException("Unknown Uri: " + uri)
        }
        return 0
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