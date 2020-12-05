package solutions

import inputs.input3
import java.awt.Point

val slope = Point(3,1)

val slopes = listOf<Point>(
        Point(1,1),
        slope,
        Point(5,1),
        Point(7,1),
        Point(1,2)
)

private fun first(input: List<String>, slope: Point): Long {

    var pos = Point(0,0)
    var trees: Long = 0

    while (pos.y < input.size) {
        if (input[pos.y][pos.x].toString() == "#")
            trees++
        pos.x = (pos.x + slope.x) % input[pos.y].length
        pos.y += slope.y
    }

    return trees
}

private fun second(input: List<String>, slopes: List<Point>): Long {
    var trees: Long = 1

    for (slope in slopes) {
        trees *= first(input, slope)
    }

    return trees
}

fun main() {
    println(first(input3.formattedInput, slope))
    println(second(input3.formattedInput, slopes))
}