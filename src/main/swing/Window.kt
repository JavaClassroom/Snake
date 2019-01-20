package main.swing

import javax.swing.*

class Window(title: String) : JFrame(){

    init {
        createUI(title)
        isVisible=true
    }

    private fun createUI(title: String) {

        setTitle(title)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(300, 200)
        setLocationRelativeTo(null)

    }
}