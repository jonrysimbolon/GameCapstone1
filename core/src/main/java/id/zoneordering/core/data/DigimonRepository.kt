package id.zoneordering.core.data

import id.zoneordering.core.data.source.local.LocalDataSource
import id.zoneordering.core.data.source.remote.RemoteDataSource
import id.zoneordering.core.data.source.remote.network.ApiResponse
import id.zoneordering.core.domain.model.Digimon
import id.zoneordering.core.domain.repository.IDigimonRepository
import id.zoneordering.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DigimonRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IDigimonRepository {

    override suspend fun getAllDigimon(): Flow<Resource<List<Digimon>>> = flow {
        emit(Resource.Loading())

        val localData = localDataSource.getAllDigimon().firstOrNull()?.let {
            DataMapper.mapEntitiesToDomain(it)
        }

        if (!localData.isNullOrEmpty()) {
            emit(Resource.Success(localData))
        } else {
            when (val response = remoteDataSource.getAllDigimon().first()) {
                is ApiResponse.Success -> {
                    val digimonList = DataMapper.mapResponsesToEntities(response.data)
                    localDataSource.insertDigimon(digimonList)
                    emitAll(
                        localDataSource.getAllDigimon()
                            .map { Resource.Success(DataMapper.mapEntitiesToDomain(it)) })
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }

                is ApiResponse.Empty -> {
                    emit(Resource.Error("Empty"))
                }
            }
        }
    }

    override suspend fun getFavoriteDigimon(): Flow<List<Digimon>> = localDataSource.getFavoriteDigimon().map {
        DataMapper.mapEntitiesToDomain(it)
    }

    override suspend fun setFavoriteDigimon(digimon: Digimon, state: Boolean) {
        val digimonEntity = DataMapper.mapDomainToEntity(digimon)
        localDataSource.setFavoriteDigimon(digimonEntity, state)
    }

    override suspend fun getDigimonByName(name: String): Flow<Digimon> =
        localDataSource.getDigimonByName(name).map {
            DataMapper.mapEntityToDomain(it)
        }
}

