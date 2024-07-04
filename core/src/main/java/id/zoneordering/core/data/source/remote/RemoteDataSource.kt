package id.zoneordering.core.data.source.remote

import id.zoneordering.core.data.source.remote.network.ApiResponse
import id.zoneordering.core.data.source.remote.network.ApiService
import id.zoneordering.core.data.source.remote.response.DigimonResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class RemoteDataSource(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getAllDigimon(): Flow<ApiResponse<List<DigimonResponse>>> {
        return flow {
            try {
                val response = apiService.getList()
                if (response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.e(e.toString())
            }
        }.flowOn(ioDispatcher)
    }
}

