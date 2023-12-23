package com.abdullah.tourismapp.core.data.source.local

import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val tourismDao: com.abdullah.tourismapp.core.data.source.local.room.TourismDao) {

    companion object {
        private var instance: com.abdullah.tourismapp.core.data.source.local.LocalDataSource? = null

        fun getInstance(tourismDao: com.abdullah.tourismapp.core.data.source.local.room.TourismDao): com.abdullah.tourismapp.core.data.source.local.LocalDataSource =
            com.abdullah.tourismapp.core.data.source.local.LocalDataSource.Companion.instance ?: synchronized(this) {
                com.abdullah.tourismapp.core.data.source.local.LocalDataSource.Companion.instance
                    ?: com.abdullah.tourismapp.core.data.source.local.LocalDataSource(tourismDao)
            }
    }

    fun getAllTourism(): Flow<List<com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity>> = tourismDao.getAllTourism()

    fun getFavoriteTourism(): Flow<List<com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity>> = tourismDao.getFavoriteTourism()

    suspend fun insertTourism(tourismList: List<com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity>) = tourismDao.insertTourism(tourismList)

    fun setFavoriteTourism(tourism: com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity, newState: Boolean) {
        tourism.isFavorite = newState
        tourismDao.updateFavoriteTourism(tourism)
    }
}