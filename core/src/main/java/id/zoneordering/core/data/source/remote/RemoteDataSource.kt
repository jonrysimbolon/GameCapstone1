package id.zoneordering.core.data.source.remote

import android.util.Log
import id.zoneordering.core.data.source.remote.network.ApiResponse
import id.zoneordering.core.data.source.remote.network.ApiService
import id.zoneordering.core.data.source.remote.response.DigimonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllDigimon(): Flow<ApiResponse<List<DigimonResponse>>> {
        //get data from remote api
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
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

