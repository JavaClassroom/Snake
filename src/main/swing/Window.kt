package main.swing

import java.awt.*
import java.awt.event.KeyEvent.*
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.*

class Window(title: String): JFrame(), KeyListener{

    override fun keyPressed(e: KeyEvent?) {
        if (e == null) return
        fun wasd(){ //первый старт игры (не рестарт) возможен клавишами управления для удобства
            if (main.swing.state == State.START)
                main.swing.state = State.GAME
        }
        when (e.keyCode) {
            VK_ESCAPE -> System.exit(0)
            VK_SPACE -> main.swing.state = State.GAME

            VK_UP, VK_W -> {snake.side = Snake.Side.UP
                wasd()}
            VK_DOWN, VK_S -> {snake.side = Snake.Side.DOWN
                wasd()}
            VK_LEFT, VK_A -> {snake.side = Snake.Side.LEFT
                wasd()}
            VK_RIGHT, VK_D -> {snake.side = Snake.Side.RIGHT
                wasd()}
        }

    }

    override fun keyReleased(e: KeyEvent?) {

    }

    override fun keyTyped(e: KeyEvent?) {

    }

    init {
        isUndecorated = true

        createUI()
        pack()
        isResizable = false
        setTitle(title)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)//null=центр экрана
        isVisible = true

        addKeyListener(this)
        isFocusable = true
        //focusableWindowState = true
        //focusTraversalKeysEnabled = false
    }

    private lateinit var panel: MyJPanel
    private lateinit var labelScope: JLabel
    private lateinit var labelText: JLabel

    private fun createUI() {
        contentPane.layout = BorderLayout()

        panel = MyJPanel()
        val panelSize = Dimension(300, 300)
        panel.preferredSize = panelSize
        panel.maximumSize = panelSize
        panel.minimumSize = panelSize
        panel.border = BorderFactory.createLineBorder(Color.BLACK)
        panel.background = Color.WHITE
        add(panel)
        add(JPanel(), BorderLayout.EAST)
        add(JPanel(), BorderLayout.WEST)
        add(JPanel(), BorderLayout.NORTH)

        //val buttonPan = JPanel(FlowLayout(FlowLayout.CENTER))
        val buttonPan = JPanel(GridLayout(2, 1))
            labelText = JLabel("новая игра? / SPACE", SwingConstants.CENTER)
            labelText.preferredSize = Dimension(300,15)
            buttonPan.add(labelText)

            labelScope = JLabel("scopes: $scopes", SwingConstants.CENTER)
            labelScope.preferredSize = Dimension(300, 15)
            buttonPan.add(labelScope)
        add(buttonPan, BorderLayout.SOUTH)
    }

    fun redraw(){panel.repaint()}
    fun redrawScope() {labelScope.text = "очки: $scopes"}
    fun redrawText(s: String) {labelText.text = s}

    private class MyJPanel: JPanel() {
        var cellSize = 0

        override fun paint(g: Graphics?) {
            super.paint(g)
            if ( g == null || width <= 0 || height <= 0 ) return
            cellSize = width/SIZE
            drawPole(g)
            drawSnake(g)
            drawFeed(g)
        }

        private fun drawPole(g: Graphics){
            g.color = Color.blue
            for (i in 1..SIZE-1){
                g.drawLine(0, i*cellSize, width, i*cellSize)
                g.drawLine(i*cellSize, 0, i*cellSize, height)
            }
        }

        private fun drawSnake(g: Graphics){
            snake.list.forEach { point -> drawCell(g, Cell.SNAKE, point.x, point.y) }
        }

        private fun drawFeed(g: Graphics){
            drawCell(g, Cell.MOUSE, feed.point.x, feed.point.y)
        }

        private fun drawCell(g: Graphics, cell: Cell, x: Int, y: Int){
            g.color = cell.color
            g.fillRect(x*cellSize+1, y*cellSize+1, cellSize-1, cellSize-1)
        }
    }

}