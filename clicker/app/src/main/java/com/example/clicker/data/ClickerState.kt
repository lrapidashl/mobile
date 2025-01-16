package com.example.clicker.data

data class ClickerState(
    var cookieCount: Double = 0.0,
    val cookiesPerSecond: Double = 0.0,
    val elapsedTime: String = "0:00",
    val averageSpeed: Double = 0.0,
    val buildings: List<Building> = emptyList()
)