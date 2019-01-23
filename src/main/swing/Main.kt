package main.swing

import java.awt.EventQueue

lateinit var window : Window
var scopes = 0
val size=10
enum class Cell{NONE, SNAKE, MOUSE}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        window = Window("swing")
    }
    pole[2][3] = Cell.MOUSE
}

val pole = Array(size, {Array(size, {Cell.NONE})})