package id.zoneordering.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.zoneordering.core.data.source.local.entity.DigimonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DigimonDao {

    @Query("SELECT * FROM digimon")
    fun getAllDigimon(): Flow<List<DigimonEntity>>

    @Query("SELECT * FROM digimon WHERE isFavorite = 1")
    fun getFavoriteDigimon(): Flow<List<DigimonEntity>>

    @Query("SELECT * FROM digimon WHERE name = :digimonName")
    fun getDigimonByName(digimonName: String): Flow<DigimonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDigimon(digimon: List<DigimonEntity>)

    @Update
    suspend fun updateFavoriteDigimon(digimon: DigimonEntity)
}
