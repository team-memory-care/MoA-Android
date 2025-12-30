package com.moa.app.feature.report

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moa.app.domain.quiz.model.QuizCategory
import com.moa.app.domain.report.usecase.FetchDailyReportUseCase
import com.moa.app.domain.report.usecase.FetchMonthlyReportUseCase
import com.moa.app.domain.report.usecase.FetchWeeklyReportUseCase
import com.moa.app.feature.report.model.DailyReportUiModel
import com.moa.app.feature.report.model.WeeklyReportUiModel
import com.moa.app.feature.report.model.toUiModel
import com.moa.app.feature.report.util.DateUtils
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val navigator: Navigator,
    private val fetchDailyReportUseCase: FetchDailyReportUseCase,
    private val fetchWeeklyReportUseCase: FetchWeeklyReportUseCase,
    private val fetchMonthlyReportUseCase: FetchMonthlyReportUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState.INIT)
    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    init {
        fetchReport()
    }

    fun setReportCycle(reportCycle: ReportCycle) {
        _uiState.update { it.copy(reportCycle = reportCycle) }
        fetchReport()
    }

    fun onPrevClick() {
        _uiState.update { currentState ->
            when (currentState.reportCycle) {
                ReportCycle.DAILY -> currentState.copy(
                    dailyDate = currentState.dailyDate.minusDays(1),
                )

                ReportCycle.WEEKLY -> currentState.copy(
                    weeklyDate = currentState.weeklyDate.minusWeeks(1),
                )

                ReportCycle.MONTHLY -> currentState.copy(
                    monthlyDate = currentState.monthlyDate.minusMonths(1),
                )
            }
        }
        fetchReport()
    }

    fun onNextClick() {
        _uiState.update { currentState ->
            when (currentState.reportCycle) {
                ReportCycle.DAILY -> currentState.copy(
                    dailyDate = currentState.dailyDate.plusDays(1),
                )

                ReportCycle.WEEKLY -> currentState.copy(
                    weeklyDate = currentState.weeklyDate.plusWeeks(1),
                )

                ReportCycle.MONTHLY -> currentState.copy(
                    monthlyDate = currentState.monthlyDate.plusMonths(1),
                )
            }
        }
        fetchReport()
    }

    fun weeklyReportQuizCategorySelected(category: QuizCategory) {
        _uiState.update {
            it.copy(weeklyReport = it.weeklyReport?.copy(selectedCategory = category))
        }
    }

    private fun fetchReport() {
        when (_uiState.value.reportCycle) {
            ReportCycle.DAILY -> fetchDailyReport()
            ReportCycle.WEEKLY -> fetchWeeklyReport()
            ReportCycle.MONTHLY -> {}
        }
    }

    private fun fetchDailyReport() {
        viewModelScope.launch {
            val date = _uiState.value.dailyDate.toString()
            fetchDailyReportUseCase(date).fold(
                onSuccess = { dailyReport ->
                    _uiState.update { it.copy(dailyReport = dailyReport?.toUiModel()) }
                },
                onFailure = {
                    Timber.d("fetchDailyReport: $it")
                },
            )
        }
    }

    private fun fetchWeeklyReport() {
        viewModelScope.launch {
            val date = _uiState.value.weeklyDate
            val year = date.year
            val month = date.monthValue
            val weekFields = WeekFields.of(Locale.KOREA)
            val week = date.get(weekFields.weekOfMonth())
            fetchWeeklyReportUseCase(year, month, week).fold(
                onSuccess = { weeklyReport ->
                    _uiState.update { it.copy(weeklyReport = weeklyReport?.toUiModel()) }
                },
                onFailure = {
                    Log.d("ReportViewModel", "fetchWeeklyReport: $it")
                },
            )
        }
    }

    private fun fetchMonthlyReport() {
        viewModelScope.launch {
            val date = _uiState.value.monthlyDate
            val year = date.year
            val month = date.monthValue
            fetchMonthlyReportUseCase(year, month).fold(
                onSuccess = {
                    Log.d("ReportViewModel", "fetchMonthlyReport: $it")
                },
                onFailure = {
                    Log.d("ReportViewModel", "fetchMonthlyReport: $it")
                },
            )
        }
    }

    fun navigateToBack() = navigator.navigateBack()
}

@Immutable
data class ReportUiState(
    val reportCycle: ReportCycle,
    val dailyDate: LocalDate,
    val weeklyDate: LocalDate,
    val monthlyDate: LocalDate,
    val dailyReport: DailyReportUiModel?,
    val weeklyReport: WeeklyReportUiModel?,
) {
    val formattedCurrentDate: String
        get() = when (reportCycle) {
            ReportCycle.DAILY -> DateUtils.formatToMonthDay(dailyDate)
            ReportCycle.WEEKLY -> DateUtils.formatToMonthWeek(weeklyDate)
            ReportCycle.MONTHLY -> DateUtils.formatToYearMonth(monthlyDate)
        }

    companion object {
        val INIT = ReportUiState(
            reportCycle = ReportCycle.DAILY,
            dailyDate = LocalDate.now(),
            weeklyDate = LocalDate.now(),
            monthlyDate = LocalDate.now(),
            dailyReport = null,
            weeklyReport = null,
        )
    }
}

enum class ReportCycle(val title: String) {
    DAILY(title = "하루"),
    WEEKLY(title = "일주일"),
    MONTHLY(title = "한달"),
}
