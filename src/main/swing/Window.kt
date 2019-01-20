package main.swing

import java.awt.GridLayout
import javax.swing.*

class Window(title: String): JFrame(){
    lateinit var table: JTable

    init {
        createUI(title)
    }

    private fun createUI(title: String) {
        setTitle(title)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 200)
        setLocationRelativeTo(null)//null=центр экрана

        contentPane.layout = GridLayout(1,1)

        table = JTable(10, 10)
        contentPane.add(table)
    }
}