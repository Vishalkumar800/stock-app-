package com.rach.stockapp.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.stockapp.domain.model.ChartModelData
import com.rach.stockapp.domain.model.CompanyOverviewModel
import com.rach.stockapp.domain.repository.StockDetailsRepository
import com.rach.stockapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val repository: StockDetailsRepository
) : ViewModel() {

    private val _companyInfoState =
        MutableStateFlow<Resources<CompanyOverviewModel>>(Resources.Loading())
    val companyInfoState: StateFlow<Resources<CompanyOverviewModel>> =
        _companyInfoState.asStateFlow()

    private val _chartData = MutableStateFlow<Resources<ChartModelData>>(Resources.Loading())
    val chartData: StateFlow<Resources<ChartModelData>> = _chartData.asStateFlow()


    fun companyInfo(symbol: String) {
        viewModelScope.launch {
            repository.companyOverView(symbol).collect { data ->
                _companyInfoState.value = data
            }
        }
    }

    fun chartData(symbol: String) {
        viewModelScope.launch {
            repository.getChartData(symbol).collect { chartInfo ->
                _chartData.value = chartInfo
            }
        }
    }


}