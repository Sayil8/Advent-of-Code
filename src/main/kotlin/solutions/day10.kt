package solutions

import inputs.input10

private fun first(input: List<String>): Int {
    val numbers = input.map { it.toInt() }
    var oneDifference = 0
    var threeDifference = 0
    var currentJolt = 0

    while (currentJolt < (numbers.maxOrNull()!!)) {
        var validOptions = setOf<Int>()
        for (i in 0..numbers.size - 1) {
            if (numbers[i] - currentJolt <= 3 && (currentJolt == 0  || numbers[i] > currentJolt))
                validOptions += numbers[i]
        }
        when (validOptions.minOrNull()!! - currentJolt){
            1 -> oneDifference++
            3 -> threeDifference++
        }
        currentJolt = validOptions.minOrNull()!!

    }

    return oneDifference * (threeDifference + 1)
}

private fun second(input: List<String>) : Long {
    val numbers = input.map { it.toInt()}
    val adapters: List<Int> = numbers.plus(0).plus(numbers.maxOrNull()!! + 3).sorted()

    val pathsByAdapter: MutableMap<Int,Long> = mutableMapOf(0 to 1L)

    adapters.drop(1).forEach { adapter ->
        pathsByAdapter[adapter] = (1 .. 3).map { lookBack ->
            pathsByAdapter.getOrDefault(adapter - lookBack, 0)
        }.sum()
    }

    return pathsByAdapter.getValue(adapters.last())
}

fun main() {
    println(first(input10.formattedInput))
    println(second(input10.formattedInput))
}