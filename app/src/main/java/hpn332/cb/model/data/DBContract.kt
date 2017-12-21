package hpn332.cb.model.data

import android.net.Uri

object DBContract {

    internal val AUTHORITY = "hpn332.cb"
    internal val BASE_URI = Uri.parse("content://" + AUTHORITY)
    internal val TABLE_PROJECT = "project"
    internal val TABLE_TASK = "task"
    internal val TABLE_TAG = "tag"
    internal val TABLE_BACKLOG = "backlog"

    val URI_PROJECT = buildUri(BASE_URI, TABLE_PROJECT)

    val URI_TASK = buildUri(BASE_URI, TABLE_TASK)

    val URI_TASK_STEP = buildUri(BASE_URI, TABLE_TASK + "step")

    val URI_TAG = buildUri(BASE_URI, TABLE_TAG)

    val URI_BACKLOG = buildUri(BASE_URI, TABLE_BACKLOG)

    class ProjectEntry {
        companion object {

            val ID = "_id"
            val TITLE = "title"
            val DESCRIPTION = "description"

            internal val CREATE_TABLE = (
                "CREATE TABLE " + TABLE_PROJECT + " ("
                    + DBContract.ProjectEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DBContract.ProjectEntry.TITLE + " TEXT,"
                    + DBContract.ProjectEntry.DESCRIPTION + " TEXT"
                    + ");")

            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.project"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.project"
        }
    }

    class TaskEntry {
        companion object {

            val ID = "_id"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val PROJECT = "project"
            val BACKLOG = "backlog"
            val TAGS = "tags"
            val STEP = "step"
            val RANK = "rank"

            internal val CREATE_TABLE = (
                "CREATE TABLE " + TABLE_TASK + " ("
                    + DBContract.TaskEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DBContract.TaskEntry.TITLE + " TEXT,"
                    + DBContract.TaskEntry.DESCRIPTION + " TEXT,"
                    + DBContract.TaskEntry.PROJECT + " INTEGER,"
                    + DBContract.TaskEntry.BACKLOG + " INTEGER,"
                    + DBContract.TaskEntry.TAGS + " TEXT,"
                    + DBContract.TaskEntry.STEP + " INTEGER,"
                    + DBContract.TaskEntry.RANK + " INTEGER"
                    + ");")

            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.task"
            internal val CONTENT_ITEM_STEP =
                "vnd.android.cursor.step.dir/vnd.$AUTHORITY.task"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.task"
        }
    }

    class TagEntry {
        companion object {

            val ID = "_id"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val COLOR = "color"

            internal val CREATE_TABLE = (
                "CREATE TABLE " + TABLE_TAG + " ("
                    + DBContract.TagEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DBContract.TagEntry.TITLE + " TEXT,"
                    + DBContract.TagEntry.DESCRIPTION + " TEXT,"
                    + DBContract.TagEntry.COLOR + "  INTEGER"
                    + ");")

            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.tag"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.tag"
        }
    }

    class BackLogEntry {
        companion object {

            val ID = "_id"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val PROJECT = "project"
            val COLOR = "color"

            internal val CREATE_TABLE = (
                "CREATE TABLE " + TABLE_BACKLOG + " ("
                    + DBContract.BackLogEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DBContract.BackLogEntry.TITLE + " TEXT,"
                    + DBContract.BackLogEntry.DESCRIPTION + " TEXT,"
                    + DBContract.BackLogEntry.PROJECT + " INTEGER,"
                    + DBContract.BackLogEntry.COLOR + "  INTEGER"
                    + ");")

            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.backlog"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.backlog"
        }
    }


    fun buildUri(URI: Uri, id: String): Uri = URI.buildUpon().appendEncodedPath(id).build()

    internal fun getPathSegments(uri: Uri): String = uri.pathSegments[1]
}
