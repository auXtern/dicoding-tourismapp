package com.abdullah.tourismapp.detail

import androidx.lifecycle.ViewModel
import com.abdullah.tourismapp.core.domain.model.Tourism
import com.abdullah.tourismapp.core.domain.usecase.TourismUseCase

class DetailTourismViewModel(private val tourismUseCase: TourismUseCase) : ViewModel() {
    fun setFavoriteTourism(tourism: Tourism, newStatus:Boolean) =
        tourismUseCase.setFavoriteTourism(tourism, newStatus)
}

