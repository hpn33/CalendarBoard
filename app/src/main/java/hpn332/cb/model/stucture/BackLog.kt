package hpn332.cb.model.stucture

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "backlog")
class BackLog(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "desc")
    var desc: String = "",

    @ColumnInfo(name = "project_id")
    var project_id: Int = 0,

    @ColumnInfo(name = "color")
    var color: Int = -16777216)