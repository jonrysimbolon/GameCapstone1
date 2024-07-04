package id.zoneordering.core.domain.usecase

import id.zoneordering.core.data.Resource
import id.zoneordering.core.domain.model.Digimon
import kotlinx.coroutines.flow.Flow

interface DigimonUseCase {
    suspend fun getAllDigimon(): Flow<Resource<List<Digimon>>>
    suspend fun getFavoriteDigimon(): Flow<List<Digimon>>
    suspend fun setFavoriteDigimon(digimon: Digimon, state: Boolean)
    suspend fun getDigimon(nama: String): Flow<Digimon>
}