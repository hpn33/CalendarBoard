package hpn332.cb.model.data

import android.net.Uri

@Deprecated("Not use")
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

            val ID = "id"
            val TITLE = "title"
            val DESCRIPTION = "description"


            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.project_id"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.project_id"
        }
    }

    class TaskEntry {
        companion object {

            val ID = "id"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val PROJECT = "project_id"
            val BACKLOG = "backlog"
            val TAGS = "tags"
            val STEP = "step"
            val RANK = "rank"


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

            val ID = "id"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val COLOR = "color"


            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.tag_id"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.tag_id"
        }
    }

    class BackLogEntry {
        companion object {

            val ID = "id"
            val TITLE = "title"
            val DESCRIPTION = "description"
            val PROJECT = "project_id"
            val COLOR = "color"


            internal val CONTENT_TYPE =
                "vnd.android.cursor.dir/vnd.$AUTHORITY.backlog"
            internal val CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/vnd.$AUTHORITY.backlog"
        }
    }


    fun buildUri(URI: Uri, id: String): Uri = URI.buildUpon().appendEncodedPath(id).build()

    internal fun getPathSegments(uri: Uri): String = uri.pathSegments[1]
}
