package com.abdullah.tourismapp.core.data

import com.abdullah.tourismapp.core.data.source.remote.RemoteDataSource
import com.abdullah.tourismapp.core.data.source.remote.network.ApiResponse
import com.abdullah.tourismapp.core.data.source.remote.response.TourismResponse
import com.abdullah.tourismapp.core.domain.model.Tourism
import com.abdullah.tourismapp.core.domain.repository.ITourismRepository
import com.abdullah.tourismapp.core.utils.AppExecutors
import com.abdullah.tourismapp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TourismRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: com.abdullah.tourismapp.core.data.source.local.LocalDataSource,
    private val appExecutors: AppExecutors
) : ITourismRepository {

    companion object {
        @Volatile
        private var instance: com.abdullah.tourismapp.core.data.TourismRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: com.abdullah.tourismapp.core.data.source.local.LocalDataSource,
            appExecutors: AppExecutors
        ): com.abdullah.tourismapp.core.data.TourismRepository =
            com.abdullah.tourismapp.core.data.TourismRepository.Companion.instance ?: synchronized(this) {
                com.abdullah.tourismapp.core.data.TourismRepository.Companion.instance
                    ?: com.abdullah.tourismapp.core.data.TourismRepository(
                        remoteData,
                        localData,
                        appExecutors
                    )
            }
    }

    override fun getAllTourism(): Flow<com.abdullah.tourismapp.core.data.Resource<List<Tourism>>> =
        object : com.abdullah.tourismapp.core.data.NetworkBoundResource<List<Tourism>, List<TourismResponse>>() {
            override fun loadFromDB(): Flow<List<Tourism>> {
                return localDataSource.getAllTourism().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Tourism>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<TourismResponse>>> =
                remoteDataSource.getAllTourism()

            override suspend fun saveCallResult(data: List<TourismResponse>) {
                val tourismList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertTourism(tourismList)
            }
        }.asFlow()

    override fun getFavoriteTourism(): Flow<List<Tourism>> {
        return localDataSource.getFavoriteTourism().map { DataMapper.mapEntitiesToDomain(it) }

    }

    override fun setFavoriteTourism(tourism: Tourism, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(tourism)
        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(tourismEntity, state) }
    }
}