package main.swing

import java.awt.*
import javax.swing.*
import main.swing.pole

class Window(title: String): JFrame(){

    init {
        createUI(title)
        pack()
        isResizable = false
        setTitle(title)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)//null=центр экрана
        isVisible = true
    }

    private fun createUI(title: String) {
        contentPane.layout = BorderLayout()
        //contentPane.layout = GridBagLayout()

        val panel = MyJPanel()
        val panelSize = Dimension(300, 300)
        panel.preferredSize = panelSize
        panel.maximumSize = panelSize
        panel.minimumSize = panelSize
        panel.border = BorderFactory.createLineBorder(Color.BLACK)
        add(panel)
        add(JPanel(), BorderLayout.EAST)
        add(JPanel(), BorderLayout.WEST)
        add(JPanel(), BorderLayout.NORTH)

        val buttonPan = JPanel(FlowLayout(FlowLayout.CENTER))
            val labelScope = JLabel("очки: $scopes", SwingConstants.CENTER)
            labelScope.preferredSize = Dimension(300, 20)
            buttonPan.add(labelScope)
        add(buttonPan, BorderLayout.SOUTH)
    }



    class MyJPanel: JPanel() {
        var cellSize = 0

        override fun paint(g: Graphics?) {
            super.paint(g)
            if ( g == null || width <= 0 || height <= 0 ) return
            cellSize = width/pole.size
            drawPole(g)
            drawAll(g)
        }

        fun drawPole(g: Graphics){
            g.color = Color.blue
            for (i in 1..pole.size-1){
                g.drawLine(0, i*cellSize, width, i*cellSize)
                g.drawLine(i*cellSize, 0, i*cellSize, height)
            }
        }

        fun drawAll(g:Graphics){
            for (y in 0..pole.size-1){
                for (x in 0.. pole.size-1){
                    drawCell(g, x, y)
                }
            }
        }

        fun drawCell(g: Graphics, x: Int, y: Int){
            g.color = when (pole[x][y]){
                Cell.NONE -> Color.WHITE
                Cell.MOUSE -> Color.GREEN
                Cell.SNAKE -> Color.ORANGE
            }
            g.fillRect(x*cellSize+1, y*cellSize+1, cellSize-2, cellSize-2)
        }
    }
}