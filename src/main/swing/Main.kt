package main.swing

import java.awt.*
import kotlin.concurrent.timer

enum class Cell(val color: Color){NONE(Color.WHITE), SNAKE(Color.RED), MOUSE(Color.GREEN)}
enum class State{START, GAME, END}

var scopes = 0
    set(value) {
        field = value
        if (scopesMax<value) scopesMax = value
        window.redrawScope()
    }
var scopesMax = 0
    set(value) {
        field = value
        window.redrawMaxScope()
    }
const val SIZE = 10
var snake = Snake()
val feed = Feed()
lateinit var window: Window

var state = State.START //состояние игры
    set(value) {
        if ((field != State.GAME) and (value == State.GAME)){//начало игры или новая
            if (field == State.END){//игра была проиграна
                //пересоздание объектов
                snake = Snake()
                feed.new()
                window.redraw()
                scopes = 0
                window.redrawScope()
            }
            field = value
            window.redrawText("")
            timer(null, true, 0, 200, action = {//новый игровой таймер
                if ((field == State.GAME) and (value == State.GAME)) {//пров. оба значения, иначе - последнее дергание
                    snake.go()
                    window.redraw()
                } else  cancel()//прекращение при смене игрового состояния
            })
        } else if ((field == State.GAME) and (value == State.END)){//проигрыш
            field = value
            window.redrawText("новая игра? / SPACE")
        }

    }

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        window = Window("Змейка")
    }
    //window = Window("Змейка")
}

class Snake {
    enum class Side(var x: Int, var y: Int){
        UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0)
    }
    val list = arrayListOf<Point>(Point(SIZE/2, SIZE/2),
        Point(SIZE/2, SIZE/2+1),
        Point(SIZE/2, SIZE/2+2))

    var side = Side.UP  //в какую сторону ползти
        set(value) {
            if (!isMoveBack(future(value))) //сторона меняется только не на обратное движение
                field = value
        }

    fun go(){
        if (isOutside(future(side)) || isCrush(future(side))){
            state = State.END
            return
        }
        val last2 = last()
        list.remove(last())
        list.add(0, future(side))
        if ( isFeed(first()) ){
            scopes++
            feed.new()
            list.add(list.size, last2)
        }
    }

    private fun isOutside(point: Point) = point.x<0 || point.y<0 || point.x>=SIZE || point.y>=SIZE
    private fun isMoveBack(point: Point) = list[1] == point
    private fun isFeed(point: Point) = feed.point == point
    private fun isCrush(point: Point) = contain(point) and (list[1] != point) and (list.last() != point)
    private fun first() = list.first()
    private fun last() = list.last()
    private fun future(s: Side) = Point(first().x+s.x, first().y+s.y)

    fun contain(point: Point) = list.contains(point)

}

class Feed {
    private fun randomPoint() = Point((0..SIZE - 1).random(), (0..SIZE - 1).random())
    lateinit var point: Point
    fun new() {
        do point = randomPoint() while (snake.contain(point))
    }

    init {
        new()
    }
}