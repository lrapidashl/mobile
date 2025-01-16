package com.example.clicker

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clicker.data.Building
import com.example.clicker.data.ClickerState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.ceil

class ClickerViewModel : ViewModel() {
    private var startTime: Long = 0
    private var incomeStage = 0;

    private val _clickerUiState = MutableStateFlow(ClickerState())
    private val _toastMessage = MutableSharedFlow<String>()

    val clickerUiState: StateFlow<ClickerState> = _clickerUiState
    val toastMessage: SharedFlow<String> = _toastMessage

    init {
        initializeBuildings()
        initTimer()
    }

    private fun initTimer() {
        startTime = System.currentTimeMillis()

        startTimer()
    }

    // TODO: Merge timers
    private fun startTimer() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            @SuppressLint("DefaultLocale")
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                val seconds = (elapsedTime / 1000) % 60
                val minutes = (elapsedTime / (1000 * 60)) % 60

                val newState = _clickerUiState.value.copy(
                    elapsedTime = String.format(
                        "%02d:%02d",
                        minutes,
                        seconds
                    )
                )

                _clickerUiState.value = newState
                updateCookieCount(_clickerUiState.value.cookiesPerSecond)

                handler.postDelayed(this, 1000)
            }
        })
    }

    private fun updateCookieCount(amount: Double) {
        _clickerUiState.value = _clickerUiState.value.copy(
            cookieCount = _clickerUiState.value.cookieCount + amount
        )
        if (_clickerUiState.value.cookieCount.toInt() / 100 > incomeStage) {
            incomeStage = _clickerUiState.value.cookieCount.toInt() / 100
            viewModelScope.launch {
                _toastMessage.emit("У тебя \"${incomeStage * 100}\" печений! На сегодня хватит, вырубай!")
            }
        }
    }

    fun onCookieClicked() {
        updateCookieCount(1.0)
    }

    private fun reduceCookies(amount: Int) {
        if (_clickerUiState.value.cookieCount >= amount) {
            updateCookieCount(-amount.toDouble())
            incomeStage = _clickerUiState.value.cookieCount.toInt() / 100
        }
    }

    private fun increaseIncome(amount: Double) {
        val updatedState = _clickerUiState.value.copy(
            cookiesPerSecond = _clickerUiState.value.cookiesPerSecond + amount,
            averageSpeed = (_clickerUiState.value.cookiesPerSecond + amount) * 60.0
        )
        _clickerUiState.value = updatedState
    }

    private fun initializeBuildings() {
        val updatedState = _clickerUiState.value.copy( buildings = listOf(
            Building(0, "Курсор", R.drawable.cursor, 0, 15, 0.1, false),
            Building(1, "Бабушка", R.drawable.grandma, 0, 100, 1.0, false),
            Building(2, "Ферма", R.drawable.farm, 0, 1100, 8.0, false),
            Building(3, "Шахта", R.drawable.mine, 0, 12000, 47.0, false),
            Building(4, "Фабрика", R.drawable.factory, 0, 130000, 260.0, false),
            Building(5, "Банк", R.drawable.bank, 0, 1400000, 1400.0, false),
            Building(6, "Храм", R.drawable.temple, 0, 20000000, 7800.0, false),
            Building(
                7,
                "Башня волшебника",
                R.drawable.wizard_tower,
                0,
                330000000,
                44000.0,
                false
            ),
            Building(
                8,
                "Космический корабль",
                R.drawable.shipment,
                0,
                510000000,
                260000.0,
                false
            )
        ))
        _clickerUiState.value = updatedState
    }

    fun buyBuilding(building: Building): Boolean {
        val currentCookies = clickerUiState.value.cookieCount

        return if (currentCookies >= building.cost) {
            reduceCookies(building.cost)
            increaseIncome(building.income)

            val updatedBuildings = _clickerUiState.value.buildings.map { build ->
                if (building.id == build.id) {
                    build.copy(
                        count = build.count + 1,
                        cost = (build.cost * 1.15).toInt()
                    )
                } else {
                    build
                }
            }
            val updatedState = _clickerUiState.value.copy(buildings = updatedBuildings);
            _clickerUiState.value = updatedState

            viewModelScope.launch {
                _toastMessage.emit("Вы купили \"${building.name}\"!")
            }
            true
        } else {
            viewModelScope.launch {
                _toastMessage.emit("Недостаточно печенек для покупки \"${building.name}\"")
            }
            false
        }
    }

    fun refundBuilding(building: Building): Boolean {
        return if (building.count > 0) {
            updateCookieCount(ceil(building.cost / 1.15).toInt().toDouble())
            increaseIncome(-building.income)

            val updatedBuildings = _clickerUiState.value.buildings.map { build ->
                if (building.id == build.id) {
                    build.copy(
                        count = build.count - 1,
                        cost = ceil(build.cost / 1.15).toInt()
                    )
                } else {
                    build
                }
            }
            val updatedState = _clickerUiState.value.copy(buildings = updatedBuildings);
            _clickerUiState.value = updatedState

            viewModelScope.launch {
                _toastMessage.emit("Вы продали \"${building.name}\"!")
            }
            true
        } else {
            viewModelScope.launch {
                _toastMessage.emit("Нечего продавать")
            }
            false
        }
    }

    fun updateBuildingsAvailability() {
        val currentCookies = _clickerUiState.value.cookieCount

        val updatedBuildings = _clickerUiState.value.buildings.map { building ->
            building.copy(isAvailable = currentCookies >= building.cost)
        }

        val updatedState = _clickerUiState.value.copy(buildings = updatedBuildings);
        _clickerUiState.value = updatedState
    }
}