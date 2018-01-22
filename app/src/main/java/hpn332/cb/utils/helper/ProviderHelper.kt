package hpn332.cb.utils.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log

import java.util.ArrayList

import hpn332.cb.model.data.DBContract
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task

@Deprecated("hey")
class ProviderHelper {

    companion object {

        private val TAG = "ProviderHelper"

        private var lastUri: Int = 0
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun init(context: Context) {
            this.context = context
        }

        fun queryListTaskByBacklogAndStep(step: Int, project: Int, backlogId: Int,
                                          arrayList: ArrayList<Task>) {
            Log.d(TAG, "queryListTaskByStep: step : $step AND Project :: $project")

            if (!arrayList.isEmpty()) arrayList.clear()

            val cursor = context.contentResolver
                .query(
                    DBContract.buildUri(DBContract.URI_TASK_STEP, step.toString()), null,
                    DBContract.TaskEntry.PROJECT + " = " + project.toString()
                        + " AND " +
                        DBContract.TaskEntry.BACKLOG + " = " + backlogId.toString(), null, null)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.ID))
                    val title = cursor.getString(cursor.getColumnIndex(DBContract.TaskEntry.TITLE))
                    val desc = cursor
                        .getString(cursor.getColumnIndex(DBContract.TaskEntry.DESCRIPTION))
                    val tag = cursor.getString(cursor.getColumnIndex(DBContract.TaskEntry.TAGS))
                    val rank = cursor.getInt(cursor.getColumnIndex(DBContract.TaskEntry.RANK))

                    arrayList.add(Task(id, title, desc, project, tag, step, rank))
                }
                cursor.close()
            }
        }

        fun queryListBacklogByProject(project: Int,
                                      arrayList: ArrayList<BackLog>) {
            Log.d(TAG, "queryListBacklogByProject: Project :: " + project)

            if (!arrayList.isEmpty()) arrayList.clear()

            val cursor = context.contentResolver
                .query(
                    DBContract.URI_BACKLOG, null,
                    DBContract.BackLogEntry.PROJECT + " = " + project.toString(), null, null)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DBContract.BackLogEntry.ID))
                    val title = cursor.getString(cursor.getColumnIndex(DBContract.BackLogEntry.TITLE))
                    val desc = cursor
                        .getString(cursor.getColumnIndex(DBContract.BackLogEntry.DESCRIPTION))
                    val color = cursor.getInt(cursor.getColumnIndex(DBContract.BackLogEntry.COLOR))

                    arrayList.add(BackLog(id, title, desc, project, color))

                    Log.d(TAG, "queryListBacklogByProject: title :: $title di :: $id")
                }
                cursor.close()
            }
        }

        fun queryListTag(arrayList: ArrayList<Tag>) {
            Log.d(TAG, "queryListTag: setup tags list")

            if (!arrayList.isEmpty()) arrayList.clear()

            val cursor = context.contentResolver
                .query(DBContract.URI_TAG, null, null, null, null)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DBContract.TagEntry.ID))
                    val title = cursor.getString(cursor.getColumnIndex(DBContract.TagEntry.TITLE))
                    val desc = cursor.getString(cursor.getColumnIndex(DBContract.TagEntry.DESCRIPTION))
                    val color = cursor.getInt(cursor.getColumnIndex(DBContract.TagEntry.COLOR))

                    arrayList.add(Tag(id, title, desc, color))
                }
                cursor.close()
            }
        }

        fun queryListProject(arrayList: ArrayList<Project>) {
            Log.d(TAG, "queryListProject: setup tags list")

            if (!arrayList.isEmpty()) arrayList.clear()

            val cursor = context.contentResolver
                .query(DBContract.URI_PROJECT, null, null, null, null)

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DBContract.ProjectEntry.ID))
                    val title = cursor.getString(cursor.getColumnIndex(DBContract.ProjectEntry.TITLE))
                    val desc = cursor.getString(cursor.getColumnIndex(DBContract.ProjectEntry.DESCRIPTION))

                    arrayList.add(Project(id, title, desc))
                }
                cursor.close()
            }
        }


        fun insertNewProject(title: String, desc: String) {

            Log.d(TAG, "insertNewProject: ")
            val values = ContentValues()

            with(values) {
                put(DBContract.TaskEntry.TITLE, title)
                put(DBContract.TaskEntry.DESCRIPTION, desc)
            }

            val uri = context.contentResolver.insert(DBContract.URI_PROJECT, values)

            lastUri = Integer.valueOf(if (uri != null) uri.pathSegments[1] else null)

            if (uri != null) Log.d(TAG, "insertNewProject: uri: " + uri)
        }

        fun insertNewBacklog(
            project: Int, title: String, desc: String, color: Int) {

            Log.d(TAG, "insertNewBacklog: pro :: $project title :: $title")

            val values = ContentValues()

            with(values) {
                put(DBContract.BackLogEntry.TITLE, title)
                put(DBContract.BackLogEntry.DESCRIPTION, desc)
                put(DBContract.BackLogEntry.PROJECT, project)
                put(DBContract.BackLogEntry.COLOR, color)
            }

            val uri = context.contentResolver.insert(DBContract.URI_BACKLOG, values)

            if (uri != null) Log.d(TAG, "insertNewBacklog: uri: " + uri)
        }

        fun insertNewTask(
            project: Int, backlog: Int, title: String,
            desc: String, tag: String, rank: Int) {

            Log.d(TAG, "insertNewTask: ")

            val values = ContentValues()

            with(values) {
                put(DBContract.TaskEntry.TITLE, title)
                put(DBContract.TaskEntry.DESCRIPTION, desc)
                put(DBContract.TaskEntry.PROJECT, project)
                put(DBContract.TaskEntry.BACKLOG, backlog)
                put(DBContract.TaskEntry.TAGS, tag)
                put(DBContract.TaskEntry.STEP, 1)
                put(DBContract.TaskEntry.RANK, rank)
            }
            val uri = context.contentResolver.insert(DBContract.URI_TASK, values)

            if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri)
        }

        fun insertNewTag(title: String, desc: String, color: Int) {

            Log.d(TAG, "insertNewTag: ")

            val values = ContentValues()

            with(values) {
                put(DBContract.TagEntry.TITLE, title)
                put(DBContract.TagEntry.DESCRIPTION, desc)
                put(DBContract.TagEntry.COLOR, color)
            }

            val uri = context.contentResolver.insert(DBContract.URI_TAG, values)

            if (uri != null) Log.d(TAG, "insertNewTask: uri: " + uri)
        }


        fun updateOneProject(id: Int, title: String, desc: String) {

            Log.d(TAG, "updateOneProject: ")

            val values = ContentValues()

            with(values) {
                put(DBContract.TaskEntry.TITLE, title)
                put(DBContract.TaskEntry.DESCRIPTION, desc)
            }

            val update = context.contentResolver
                .update(DBContract.buildUri(DBContract.URI_PROJECT, id.toString()),
                    values, null, null)

            Log.d(TAG, "updateOneProject: update: " + update)
        }

        fun updateOneTask(
            id: Int, title: String, desc: String, tag: String, step: Int, rank: Int) {

            Log.d(TAG, "updateOneTask: ")

            val values = ContentValues()

            with(values) {
                put(DBContract.TaskEntry.TITLE, title)
                put(DBContract.TaskEntry.DESCRIPTION, desc)
                put(DBContract.TaskEntry.TAGS, tag)
                put(DBContract.TaskEntry.STEP, step)
                put(DBContract.TaskEntry.RANK, rank)
            }
            val update = context.contentResolver
                .update(DBContract.buildUri(DBContract.URI_TASK, id.toString()),
                    values, null, null)

            Log.d(TAG, "updateOneTask: update: " + update)
        }

        fun updateOneTaskToNextStep(
            id: Int, step: Int) {

            Log.d(TAG, "updateOneTaskToNextStep: ")

            val values = ContentValues()

            values.put(DBContract.TaskEntry.STEP, step)

            val update = context.contentResolver
                .update(DBContract.buildUri(DBContract.URI_TASK, id.toString()),
                    values, null, null)

            Log.d(TAG, "updateOneTaskToNextStep: update: " + update)
        }

        fun updateOneTag(
            id: Int, title: String, desc: String, color: Int) {

            Log.d(TAG, "updateOneTag: ")

            val values = ContentValues()

            with(values) {
                put(DBContract.TagEntry.TITLE, title)
                put(DBContract.TagEntry.DESCRIPTION, desc)
                put(DBContract.TagEntry.COLOR, color)
            }
            val update = context.contentResolver
                .update(
                    DBContract.buildUri(DBContract.URI_TAG, id.toString()),
                    values, null, null)

            Log.d(TAG, "updateOneTag: update: " + update)
        }

        fun updateOneBacklog(
            id: Int, title: String, desc: String, color: Int) {

            Log.d(TAG, "updateOneTag: start")

            val values = ContentValues()

            with(values) {
                put(DBContract.BackLogEntry.TITLE, title)
                put(DBContract.BackLogEntry.DESCRIPTION, desc)
                put(DBContract.BackLogEntry.COLOR, color)
            }

            val update = context.contentResolver
                .update(
                    DBContract.buildUri(DBContract.URI_BACKLOG, id.toString()),
                    values, null, null)

            Log.d(TAG, "updateOneBacklog: end update: " + update)
        }


        fun deleteOneProject(id: Int) {

            Log.d(TAG, "deleteOneProject: ")

            val delete = context.contentResolver.delete(DBContract.buildUri(DBContract.URI_PROJECT, id.toString()), null, null)

            Log.d(TAG, "deleteOneProject: delete project_id id :: " + delete)
        }

        fun deleteOneTask(id: Int) {

            Log.d(TAG, "deleteOneTask: ")

            val delete = context.contentResolver.delete(DBContract.buildUri(DBContract.URI_TASK, id.toString()), null, null)

            Log.d(TAG, "deleteOneTask: delete task id :: " + delete)
        }

        fun deleteOneTag(id: Int) {

            Log.d(TAG, "deleteOneTag: ")

            val delete = context.contentResolver.delete(DBContract.buildUri(DBContract.URI_TAG, id.toString()), null, null)

            Log.d(TAG, "deleteOneTag: delete tag_id id :: " + delete)
        }

        fun deleteOneBacklog(id: Int) {

            Log.d(TAG, "deleteOneBacklog: ")

            val delete = context.contentResolver.delete(DBContract.buildUri(DBContract.URI_BACKLOG, id.toString()), null, null)

            Log.d(TAG, "deleteOneBacklog: delete backlog id :: " + delete)
        }

        ///------------------------------------------------------
        ///------------------------------------------------------
        ///------------------------------------------------------

        fun insertNewProjectWithBacklog(title: String, desc: String) {

            insertNewProject(title, desc)

            insertNewBacklog(lastUri, "BASE", "BASE Backlog", -1)

            Log.d(TAG, "insertNewProjectWithBacklog: done")
        }
    }
}
