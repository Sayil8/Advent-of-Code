package solutions

import inputs.input24


class day24(private val input: List<String>) {

    private fun String.walkPath(): Point3D =
            splitPattern
                    .findAll(this)
                    .map { it.value }
                    .fold(Point3D.ORIGIN) { last, dir ->
                        last.hexNeighbor(dir)
                    }

    companion object {
        private val splitPattern = "([ns]?[ew])".toRegex()
    }

    fun decorateFloor(): Set<Point3D> =
            input
                    .map { it.walkPath() }
                    .groupBy { it }
                    .filter { it.value.size % 2 == 1  }
                    .keys

    private fun Set<Point3D>.nextFloor(): Set<Point3D> {
        val pointsToEvaluate = this + (this.flatMap { point -> point.hexNeighbors })
        return pointsToEvaluate.filter { tile ->
            val adjacentBlackTiles = tile.hexNeighbors.count { it in this }
            val black = tile in this
            when {
                black && (adjacentBlackTiles == 0 || adjacentBlackTiles > 2) -> false
                !black && adjacentBlackTiles == 2 -> true
                else -> black
            }
        }.toSet()
    }

    fun solvePart2(): Int =
            generateSequence(decorateFloor()) { it.nextFloor() }
                    .drop(100)
                    .first()
                    .size

}

private fun first(input: List<String>) : Int {
    return day24(input).decorateFloor().size
}

private fun second(input: List<String>) : Int {
    return day24(input).solvePart2()
}

fun main() {
    println(first(input24.formattedInput))
    println(second(input24.formattedInput))
}