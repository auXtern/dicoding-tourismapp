package com.abdullah.tourismapp.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TourismDao {

    @Query("SELECT * FROM tourism")
    fun getAllTourism(): Flow<List<com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity>>

    @Query("SELECT * FROM tourism where isFavorite = 1")
    fun getFavoriteTourism(): Flow<List<com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTourism(tourism: List<com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity>)

    @Update
    fun updateFavoriteTourism(tourism: com.abdullah.tourismapp.core.data.source.local.entity.TourismEntity)
}