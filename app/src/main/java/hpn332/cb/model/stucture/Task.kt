package hpn332.cb.model.stucture

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "task")
class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "desc")
    var desc: String = "",

    @ColumnInfo(name = "project_id")
    var project_id: Int = 0,

    @ColumnInfo(name = "tag_id")
    var tag_id: String = "",

    @ColumnInfo(name = "step")
    var step: Int = 0,

    @ColumnInfo(name = "rank")
    var rank: Int = 0)
