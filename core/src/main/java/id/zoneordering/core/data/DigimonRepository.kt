package id.zoneordering.core.data

import id.zoneordering.core.data.source.local.LocalDataSource
import id.zoneordering.core.data.source.remote.RemoteDataSource
import id.zoneordering.core.data.source.remote.network.ApiResponse
import id.zoneordering.core.data.source.remote.response.DigimonResponse
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.domain.repository.IDigimonRepository
import id.zoneordering.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DigimonRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IDigimonRepository {

    override fun getAllDigimon(): Flow<Resource<List<Digimon>>> =
        object : NetworkBoundResource<List<Digimon>, List<DigimonResponse>>() {
            override suspend fun loadFromDB(): Flow<List<Digimon>> {
                return localDataSource.getAllDigimon().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Digimon>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<DigimonResponse>>> =
                remoteDataSource.getAllDigimon()

            override suspend fun saveCallResult(data: List<DigimonResponse>) {
                val digimonList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertDigimon(digimonList)
            }
        }.asFlow()

    override fun getFavoriteDigimon(): Flow<List<Digimon>> {
        return localDataSource.getFavoriteDigimon().map {
           DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteDigimon(digimon: Digimon, state: Boolean) {
        val digimonEntity = DataMapper.mapDomainToEntity(digimon)
        localDataSource.setFavoriteDigimon(digimonEntity, state)
    }

    override fun getDigimonByName(name: String): Flow<Digimon> {
        return localDataSource.getDigimonByName(name).map {
            DataMapper.mapEntityToDomain(it)
        }
    }
}

