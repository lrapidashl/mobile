package com.example.second

sealed interface Vehicle

fun f(v: Vehicle) {
    when (v) {
        Scooter -> println("Scooter")
        is Bicycle -> println("Bicycle")
        is Car -> println("Car")
    }

    val animal = Animal()

    val car = animal as? Car

    if (car != null) {

    }
}

class Animal

data object Scooter : Vehicle

data class Bicycle(
    val brand: String,
    val frontWheel: Wheel,
    val backWheel: Wheel,
    val frame: Frame
) : Vehicle

sealed interface Car : Vehicle

data class EngineCar(
    val engine: Engine,
    val wheels: List<Wheel>,
    val handlebar: Handlebar
) : Car

data class ElectricCar(
    val electricEngine: ElectricEngine,
    val wheels: List<Wheel>,
    val autopilot: Autopilot
) : Car

enum class FrameMaterial {
    Steel, Aluminum, Wood
}

data class Frame(
    val material: FrameMaterial
)

data class Wheel(
    val diameter: Double,
    val brand: String,
    val disc: DiscType
)

enum class DiscType {
    Cast, Forged, Stamped
}

data class Engine(
    val volume: Double,
    val fuelType: FuelType
)

enum class FuelType {
    Diesel, Gas92, Gas95, Gas98, Gas100
}

data class Handlebar(
    val type: String
)

data class ElectricEngine(
    val power: Double
)

enum class Autopilot {
    Yandex, Tesla
}

fun main() {

}