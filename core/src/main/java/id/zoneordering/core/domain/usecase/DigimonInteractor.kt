package id.zoneordering.core.domain.usecase

import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.domain.repository.IDigimonRepository
import kotlinx.coroutines.flow.Flow

class DigimonInteractor(private val digimonRepository: IDigimonRepository): DigimonUseCase {

    override suspend fun getAllDigimon() = digimonRepository.getAllDigimon()

    override suspend fun getFavoriteDigimon() = digimonRepository.getFavoriteDigimon()

    override suspend fun setFavoriteDigimon(digimon: Digimon, state: Boolean) = digimonRepository.setFavoriteDigimon(digimon, state)

    override suspend fun getDigimon(nama: String): Flow<Digimon> = digimonRepository.getDigimonByName(nama)
}