package solutions

import inputs.input17


interface Point {
    val neighbors: List<Point>
}

class Day17(private val input: List<String>) {

    fun solvePart1() =
            solve { x, y ->
                Point3D(x, y, 0)
            }

    fun solvePart2(): Int =
            solve { x, y ->
                Point4D(x, y, 0, 0)
            }

    private fun solve(rounds: Int = 6, pointFunction: (Int, Int) -> Point): Int {
        var conwayGrid = parseInput(input, pointFunction)
        repeat(rounds) {
            conwayGrid = conwayGrid.nextCycle()
        }
        return conwayGrid.count { it.value }
    }

    private fun Map<Point, Boolean>.nextCycle(): Map<Point, Boolean> {
        val nextMap = this.toMutableMap()
        keys.forEach { point ->
            point.neighbors.forEach { neighbor ->
                nextMap.putIfAbsent(neighbor, false)
            }
        }
        nextMap.entries.forEach { (point, active) ->
            val activeNeighbors = point.neighbors.count { this.getOrDefault(it, false) }
            nextMap[point] = when {
                active && activeNeighbors in setOf(2, 3) -> true
                !active && activeNeighbors == 3 -> true
                else -> false
            }
        }
        return nextMap
    }

    private fun parseInput(input: List<String>, pointFunction: (Int, Int) -> Point): Map<Point, Boolean> =
            input.flatMapIndexed { x, row ->
                row.mapIndexed { y, point ->
                    pointFunction(x, y) to (point == '#')
                }
            }.toMap()

}

fun main() {
    println(Day17(input17.formattedInput).solvePart1())
    println(Day17(input17.formattedInput).solvePart2())
}

