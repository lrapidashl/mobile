package com.example.simplefunctions

import kotlin.math.pow

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun subtract(a: Int, b: Int): Int {
    return a - b
}

fun divide(a: Int, b: Int): Double {
    return (a / b).toDouble()
}

fun multiply(a: Int, b: Int): Int {
    return a * b
}

fun pow(a: Int, b: Int): Double {
    return a.toDouble().pow(b)
}

fun max(a: Array<Int>): Int {
    return a.max()
}

fun min(a: Array<Int>): Int {
    return a.min()
}

fun printList(a: Array<Int>): String {
    return a.sort().joinа ToString(" < ")
}



fun main() {
    val line: String = readlnOrNull().toString()
    val parts = line.split(' ')
    when (parts[0]) {
        "sum" -> println("Сумма чисел " + parts[1] + " и " + parts[2] + " равна " + sum(parts[1].toInt(), parts[2].toInt()))
        "subtract" -> println("Разность чисел " + parts[1] + " и " + parts[2] + " равна " + subtract(parts[1].toInt(), parts[2].toInt()))
    }
}
