package solutions

import inputs.input12
import kotlin.math.absoluteValue

sealed class Direction {
    abstract val turnLeft: Direction
    abstract val turnRight: Direction
    abstract val offset: Point2D

    operator fun invoke(dir: String): Direction =
            when (dir) {
                "N" -> North
                "S" -> South
                "E" -> East
                "W" -> West
                else -> throw IllegalArgumentException("No such direction $dir")
            }

    object North : Direction() {
        override val turnLeft = West
        override val turnRight = East
        override val offset = Point2D(-1, 0)
    }

    object South : Direction() {
        override val turnLeft = East
        override val turnRight = West
        override val offset = Point2D(1, 0)
    }

    object West : Direction() {
        override val turnLeft = South
        override val turnRight = North
        override val offset = Point2D(0, -1)
    }

    object East : Direction() {
        override val turnLeft = North
        override val turnRight = South
        override val offset = Point2D(0, 1)
    }
}

data class Point2D(val x: Int, val y: Int) {
    operator fun plus(other: Point2D): Point2D =
            Point2D(x + other.x, y + other.y)

    operator fun times(by: Int): Point2D =
            Point2D(x * by, y * by)

    infix fun distanceTo(other: Point2D): Int =
            (x - other.x).absoluteValue + (y - other.y).absoluteValue

    companion object {
        val ORIGIN = Point2D(0, 0)
    }

    fun rotateLeft(): Point2D =
            Point2D(x = y * -1, y = x)

    fun rotateRight(): Point2D =
            Point2D(x = y, y = x * -1)
}

data class Ship(val at: Point2D = Point2D.ORIGIN, val facing: Direction = Direction.East) {

    fun forward(amount: Int): Ship =
            copy(at = at + (facing.offset * amount))

    fun move(dir: Direction, amount: Int): Ship =
            copy(at = at + (dir.offset * amount))

    fun turnLeft(times: Int): Ship =
            (0 until times).fold(this) { carry, _ ->
                carry.copy(facing = carry.facing.turnLeft)
            }

    fun turnRight(times: Int): Ship =
            (0 until times).fold(this) { carry, _ ->
                carry.copy(facing = carry.facing.turnRight)
            }
}

data class Waypoint(val at: Point2D = Point2D(-1, 10)) {
    fun move(dir: Direction, amount: Int): Waypoint =
            Waypoint(at + (dir.offset * amount))

    fun turnLeft(amount: Int): Waypoint =
            (0 until amount).fold(this) { carry, _ ->
                Waypoint(carry.at.rotateLeft())
            }

    fun turnRight(amount: Int): Waypoint =
            (0 until amount).fold(this) { carry, _ ->
                Waypoint(carry.at.rotateRight())
            }
}


private fun first(input: List<String>) : Int =
    input.fold(Ship(Point2D.ORIGIN, Direction.East)) { ship, instruction ->
        val command = instruction.first()
        val amount = instruction.substring(1).toInt()
        when (command) {
            'N' -> ship.move(Direction.North, amount)
            'S' -> ship.move(Direction.South, amount)
            'E' -> ship.move(Direction.East, amount)
            'W' -> ship.move(Direction.West, amount)
            'F' -> ship.forward(amount)
            'L' -> ship.turnLeft(amount / 90)
            'R' -> ship.turnRight(amount / 90)
            else -> throw IllegalArgumentException("Unknown instruction: $instruction")
        }
    }.at distanceTo Point2D.ORIGIN

private fun second(input: List<String>): Int {
    var waypoint = Waypoint()
    var ship = Ship()
    input.forEach { instruction ->
        val command = instruction.first()
        val amount = instruction.substring(1).toInt()
        when (command) {
            'N' -> waypoint = waypoint.move(Direction.North, amount)
            'S' -> waypoint = waypoint.move(Direction.South, amount)
            'E' -> waypoint = waypoint.move(Direction.East, amount)
            'W' -> waypoint = waypoint.move(Direction.West, amount)
            'F' -> ship = ship.copy(at = ship.at + (waypoint.at * amount))
            'L' -> waypoint = waypoint.turnLeft(amount / 90)
            'R' -> waypoint = waypoint.turnRight(amount / 90)
            else -> throw IllegalArgumentException("Unknown instruction: $instruction")
        }
    }
    return Point2D.ORIGIN distanceTo ship.at
}

fun main() {
    println(first(input12.formattedInput))
    println(second(input12.formattedInput))
}