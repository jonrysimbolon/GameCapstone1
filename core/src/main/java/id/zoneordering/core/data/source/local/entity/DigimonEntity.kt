package id.zoneordering.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "digimon")
data class DigimonEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "img")
    var photo: String,

    @ColumnInfo(name = "level")
    var level: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean,
)
