package id.zoneordering.core.utils

import id.zoneordering.core.data.source.local.entity.DigimonEntity
import id.zoneordering.core.data.source.remote.response.DigimonResponse
import id.zoneordering.core.domain.model.Digimon

object DataMapper {
    fun mapResponsesToEntities(input: List<DigimonResponse>): List<DigimonEntity> {
        val digimonList = ArrayList<DigimonEntity>()
        input.map {
            val digimon = DigimonEntity(
                name = it.name,
                photo = it.photo,
                level =  it.level,
                isFavorite = false
            )
            digimonList.add(digimon)
        }
        return digimonList
    }

    fun mapEntitiesToDomain(input: List<DigimonEntity>): List<Digimon> =
        input.map {
            Digimon(
                name = it.name,
                photo = it.photo,
                level = it.level,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntityToDomain(input: DigimonEntity): Digimon =
        Digimon(
            name = input.name,
            photo = input.photo,
            level = input.level,
            isFavorite = input.isFavorite
        )

    fun mapDomainToEntity(input: Digimon) = DigimonEntity(
        name = input.name,
        photo = input.photo,
        level = input.level,
        isFavorite = input.isFavorite
    )
}