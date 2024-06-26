package id.zoneordering.core.utils

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import id.zoneordering.core.domain.model.Digimon

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

val emptyDigimon = Digimon(
    name = "",
    photo = "",
    level = "",
    isFavorite = false
)