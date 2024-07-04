package id.zoneordering.core.data.source.local

import id.zoneordering.core.data.source.local.entity.DigimonEntity
import id.zoneordering.core.data.source.local.room.DigimonDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(
    private val digimonDao: DigimonDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getAllDigimon(): Flow<List<DigimonEntity>> = digimonDao.getAllDigimon().flowOn(ioDispatcher)

    fun getFavoriteDigimon() = digimonDao.getFavoriteDigimon().flowOn(ioDispatcher)

    suspend fun insertDigimon(digimonList: List<DigimonEntity>) =
        digimonDao.insertDigimon(digimonList)

    suspend fun setFavoriteDigimon(digimon: DigimonEntity, newState: Boolean) {
        digimon.isFavorite = newState
        digimonDao.updateFavoriteDigimon(digimon)
    }

    fun getDigimonByName(name: String) = digimonDao.getDigimonByName(name).flowOn(ioDispatcher)
}