package hpn332.cb.model.stucture

import android.arch.persistence.room.Entity

@Entity
data class Tag(
    val id: Int,
    val title: String,
    val desc: String,
    val color: Int)
