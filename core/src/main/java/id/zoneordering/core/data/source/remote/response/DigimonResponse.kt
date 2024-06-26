package id.zoneordering.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DigimonResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("img")
    val photo: String,

    @field:SerializedName("level")
    val level: String
)

