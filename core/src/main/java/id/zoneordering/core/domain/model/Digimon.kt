package id.zoneordering.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Digimon(
    val name: String,
    val photo: String,
    val level: String,
    val isFavorite: Boolean
) : Parcelable