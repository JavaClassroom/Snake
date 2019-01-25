package main.swing

import tornadofx.move
import java.awt.*
import kotlin.concurrent.timer

enum class Cell(val color: Color){NONE(Color.WHITE), SNAKE(Color.RED), MOUSE(Color.GREEN)}
enum class Side(var x: Int, var y: Int){
    STOP(0, 0), UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0)
}
var scopes = 0
val SIZE = 10
val snake = Snake()
val feed = Feed()
var side = Side.STOP
var oldSide = side
lateinit var window: Window
var endGame = false

fun main(args: Array<String>) {
    /*EventQueue.invokeLater {
        window = Window("Змейка")
    }*/
    window = Window("Змейка")
    timer(null, true, 0, 300, action = {
        if ((side != Side.STOP) and !endGame){
            snake.go()
            window.redraw()
        }
    })
}

class Snake {
    val list = arrayListOf(
        Point(SIZE/2, SIZE/2),
        Point(SIZE/2, SIZE/2+1),
        Point(SIZE/2, SIZE/2+2))
    private lateinit var last: Point
    private lateinit var first: Point
    val future = Point(0, 0)

    fun go(){
        last = snake.list.last()    //хвост
        first = snake.list.first()  //голова
        future.move(first.x+side.x, first.y+side.y) //ожидаемая новая позиция головы
        if (future.x<0 || future.x>=SIZE || future.y<0 || future.y>= SIZE   //вляпались в край поля
            || (list.contains(future) and (future.location!=list[1]) and (future.location!=last))) {  //вляпались в себя
            endGame()
            return
        }
        if (future.location == list[1].location) { //нельзя ползти назад
            side = oldSide  //меняем направление на предыдущее
            future.move(first.x+side.x, first.y+side.y) //новая позиция головы будет по старому направлению
        }
        last.location = future.location  //смена координат последнего блока на будущего первого
        snake.list.move(last, 0)    //последний блок -> первый блок
        oldSide = side  //запоминаем новое направление
        if (last.location == feed.point.location) {   //если поели
            scopes++
            feed.new()  //теперь еда в другом месте
            var last2 = Point(list.last().x, list.last().y) //запоминаем где был хвост
            go()    //еще растем вперед
            list.add(last2)     //увеличиваем на 1 блок
        }
    }
}

class Feed {
    private fun randomPoint() = Point((0..SIZE-1).random(), (0..SIZE-1).random())
    var point = randomPoint()
    fun new(){
        while (snake.list.contains(point)) point = randomPoint()
    }
    constructor(){
        new()
    }
}

fun endGame(){
    endGame = true
}