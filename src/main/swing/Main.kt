package main.swing

import java.awt.EventQueue

lateinit var window : Window

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        window = Window("swing")
        window.isVisible=true
    }
}
