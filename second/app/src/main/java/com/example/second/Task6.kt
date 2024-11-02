package com.example.second

data class Student(
    val id: Int,
    var name: String,
    val age: Int,
    var points: Int,
)

class StudentManager {
    private val students = mutableListOf<Student>()
    private var curStudentId = 1

    fun addStudent(info: String) {
        val parts = info.split(" ")
        if (parts.size != 3) {
            println("Неправильно введены данные")
            return
        }
        val name = parts[0]
        val age = parts[1].toIntOrNull() ?: return
        val points = parts[2].toIntOrNull() ?: return

        val student = Student(curStudentId++, name, age, points)
        students.add(student)
    }

    fun removeStudent(id: Int) {
        if (!students.removeIf { it.id == id }) {
            println("Студент не найден")
        }
    }

    fun updatePoints(id: Int, newPoints: Int) {
        students.find { it.id == id }?.points = newPoints
    }

    fun renameStudent(id: Int, newName: String) {
        val curStudent = students.find { it.id == id }
        if (curStudent != null) {
            students.removeIf { it.id == id }
            students.add(curStudent.copy(name = newName))
        }
    }

    fun printSortedByPoints() {
        students.sortedByDescending { it.points }
            .forEach {
                println("${it.id} ${it.name} (${it.age} лет) - ${it.points} балла")
            }
    }

    fun printSortedByNames() {
        println(students.sortedBy { it.name }.joinToString (separator = "\n", transform = {
            "${it.id} ${it.name} (${it.age} лет) - ${it.points} балла"
        }))
    }
}

fun main() {
    val studentManager = StudentManager()
    println("Введите информацию о студентах в формате: <имя> <возраст> <балл>, <имя> <возраст> <балл>, ...")
    val input = readlnOrNull()
    input?.split(", ")?.forEach { studentManager.addStudent(it) }

    while (true) {
        println("Введите команду:")
        val command = readlnOrNull() ?: break
        val parts = command.split(" ")

        when (parts[0]) {
            "add" -> {
                studentManager.addStudent(parts.drop(1).joinToString(" "))
            }

            "remove" -> {
                if (parts.size > 1) {
                    val id = parts[1].toIntOrNull()
                    if (id != null) {
                        studentManager.removeStudent(id)
                    }
                }
            }

            "update_points" -> {
                if (parts.size > 2) {
                    val id = parts[1].toIntOrNull()
                    val newPoints = parts[2].toIntOrNull()
                    if (id != null && newPoints != null) studentManager.updatePoints(id, newPoints)
                }
            }

            "rename" -> {
                if (parts.size > 2) {
                    val id = parts[1].toIntOrNull()
                    val newName = parts[2]
                    if (id != null) studentManager.renameStudent(id, newName)
                }
            }

            "print_sort_by_points" -> {
                studentManager.printSortedByPoints()
            }

            "print_sort_by_names" -> {
                studentManager.printSortedByNames()
            }

            "exit" -> break
            else -> println("Неизвестная команда")
        }
    }
}