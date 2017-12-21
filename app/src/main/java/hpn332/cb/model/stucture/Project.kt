package hpn332.cb.model.stucture

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity
data class Project(
    @PrimaryKey
    val id: Int,
    val title: String,
    val desc: String)
