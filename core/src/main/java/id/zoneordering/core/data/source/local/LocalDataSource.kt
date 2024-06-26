package id.zoneordering.core.data.source.local

import id.zoneordering.core.data.source.local.entity.DigimonEntity
import id.zoneordering.core.data.source.local.room.DigimonDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val digimonDao: DigimonDao) {

    fun getAllDigimon(): Flow<List<DigimonEntity>> = digimonDao.getAllDigimon()

    fun getFavoriteDigimon(): Flow<List<DigimonEntity>> = digimonDao.getFavoriteDigimon()

    suspend fun insertDigimon(digimonList: List<DigimonEntity>) = digimonDao.insertDigimon(digimonList)

    suspend fun setFavoriteDigimon(digimon: DigimonEntity, newState: Boolean) {
        digimon.isFavorite = newState
        digimonDao.updateFavoriteDigimon(digimon)
    }

    fun getDigimonByName(name: String) = digimonDao.getDigimonByName(name)
}