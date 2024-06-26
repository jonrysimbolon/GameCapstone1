package id.zoneordering.core.data.source.remote.network

import id.zoneordering.core.data.source.remote.response.DigimonResponse
import retrofit2.http.GET

interface ApiService {
    @GET("digimon")
    suspend fun getList(): List<DigimonResponse>
}
