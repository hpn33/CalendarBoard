package hpn332.cb.model.stucture

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Task(
    @PrimaryKey
    val id: Int,
    val title: String,
    val desc: String,
    val project: Int,
    val tag: String,
    val step: Int,
    val rank: Int)
