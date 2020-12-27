package solutions

import inputs.input15

private fun first(input: MutableList<Int>) : Int {
    var spokenNumbers : MutableList<Int> = input
    var turn = input.size
    var current = input.last()
    while ( turn < 2020) {
        if (!spokenNumbers.dropLast(1).contains(current))
         {
            spokenNumbers.add(0)
            current = 0
        } else {
            current = turn - (spokenNumbers.dropLast(1).indexOfLast { it == current } + 1)
            spokenNumbers.add(current)
        }
        turn++
    }
    return current
}

private fun second(input: MutableList<Int>, ) : Sequence<Int> = sequence {
    yieldAll(input)
    val memory = input.mapIndexed { index, i -> i to index }.toMap().toMutableMap()
    println(memory)
    var turns = input.size
    var sayNext = 0
    while(true) {
        yield(sayNext)
        val lastTimeSpoken = memory[sayNext] ?: turns
        memory[sayNext] = turns
        sayNext = turns - lastTimeSpoken
        turns++
    }
}

fun main() {
    println(first(input15.formattedInput))
    println(second(input15.formattedInput).drop(30_000_000 -1).first())
}