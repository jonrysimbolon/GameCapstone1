package id.zoneordering.core.domain.repository

import id.zoneordering.core.data.Resource
import id.zoneordering.core.domain.model.Digimon
import kotlinx.coroutines.flow.Flow

interface IDigimonRepository {

    fun getAllDigimon(): Flow<Resource<List<Digimon>>>

    fun getFavoriteDigimon(): Flow<List<Digimon>>

    suspend fun setFavoriteDigimon(digimon: Digimon, state: Boolean)

    fun getDigimonByName(name: String): Flow<Digimon>

}