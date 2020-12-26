package solutions

import inputs.input13


private fun first(input : List<String>) : Int {
    val map = hashMapOf<Int,Int>()
    val earliestDepart: Int = input.first().toInt()
    val ids: List<Int>  = input
                          .last()
                          .split(",")
                          .mapNotNull { s -> if (s == "x") null else s.toInt() }

    ids.forEach {
        var result : Int = earliestDepart / it
        while ((result * it) < earliestDepart) {
            result += 1
        }
        map[it] = result * it
    }

    var smallestTime = map.values.first()
    var selectedId = map.keys.first()
    map.forEach {
        if (it.value < smallestTime) {
            smallestTime = it.value
            selectedId = it.key
        }
    }

    return (smallestTime - earliestDepart) * selectedId
}


data class IndexedBus(val index: Int, val bus: Long)

private fun second(input: List<String>) : Long {

    val indexedBusses: List<IndexedBus> = input
        .last()
        .split(",")
        .mapIndexedNotNull { index, s -> if (s == "x") null else IndexedBus(index, s.toLong()) }

    var time = 0L
    var stepSize = indexedBusses.first().bus

    indexedBusses.drop(1).forEach { (offset, bus) ->
        while ((time + offset) % bus != 0L) {
            time += stepSize
        }
        stepSize *= bus
    }
    return time
}

fun main() {
    println(first(input13.formattedInput))
    println(second(input13.formattedInput))
}