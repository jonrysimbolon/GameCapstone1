package id.zoneordering.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.zoneordering.core.data.source.local.entity.DigimonEntity

@Database(entities = [DigimonEntity::class], version = 1, exportSchema = false)
abstract class DigimonDatabase : RoomDatabase() {

    abstract fun digimonDao(): DigimonDao

}