package hpn332.cb.model.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import hpn332.cb.model.stucture.BackLog
import hpn332.cb.model.stucture.Project
import hpn332.cb.model.stucture.Tag
import hpn332.cb.model.stucture.Task

/**
 * Created by hpn332 on 21/12/2017.
 */
@Database(entities = [Project::class, Task::class, Tag::class, BackLog::class],
    version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun taskDao(): TaskDao
    abstract fun tagDao(): TagDao
    abstract fun backlogDao(): BackLogDao


    companion object {


        private var INSTANCE: RoomDB? = null

        private val db_name = "cb.db"
        fun build(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?:
                    Room.databaseBuilder(context, RoomDB::class.java, db_name)
                        .build()
                        .also { INSTANCE = it }
            }
    }
}