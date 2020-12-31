package solutions

import inputs.input23

fun Char.asInt(): Int =
        this.toString().toInt()

fun Iterable<Long>.product(): Long =
        this.reduce { a, b -> a * b }

class Day23(private val input: String) {

    fun solvePart1(roundsToPlay: Int): String =
            Cups(input)
                    .playRounds(roundsToPlay)
                    .toString()

    fun solvePart2(roundsToPlay: Int): Long =
            Cups(input, 1_000_000)
                    .playRounds(roundsToPlay)
                    .nextAsList(2)
                    .map { it.value.toLong() }
                    .product()

    private class Cups(order: String, numberOfCups: Int = order.length) {
        val cups: List<Cup> = List(numberOfCups+1) { Cup(it) }
        var currentCup: Cup = cups[order.first().asInt()]

        init {
            val cupIdsInOrder = order.map { it.asInt() } + (order.length + 1 .. numberOfCups)
            cupIdsInOrder
                    .map { cups[it] }
                    .fold(cups[order.last().asInt()]) { previous, cup ->
                        cup.also { previous.next = cup }
                    }
            cups[cupIdsInOrder.last()].next = cups[cupIdsInOrder.first()]
        }

        fun playRounds(numRounds: Int): Cup {
            repeat(numRounds) {
                playRound()
            }
            return cups[1]
        }

        private fun playRound() {
            val next3: List<Cup> = currentCup.nextAsList(3)
            val destination = calculateDestination(next3.map { it.value }.toSet())
            moveCups(next3, destination)
            currentCup = currentCup.next
        }

        private fun moveCups(cupsToInsert: List<Cup>, destination: Cup) {
            val prevDest = destination.next
            currentCup.next = cupsToInsert.last().next
            destination.next = cupsToInsert.first()
            cupsToInsert.last().next = prevDest
        }

        private fun calculateDestination(exempt: Set<Int>): Cup {
            var dest = currentCup.value - 1
            while(dest in exempt || dest == 0) {
                dest = if(dest == 0) cups.size-1 else dest -1
            }
            return cups[dest]
        }
    }

    private class Cup(val value: Int) {
        lateinit var next: Cup

        fun nextAsList(n: Int): List<Cup> =
                (1 .. n).runningFold(this) { cur, _ -> cur.next }.drop(1)

        override fun toString(): String = buildString {
            var current = this@Cup.next
            while(current != this@Cup) {
                append(current.value.toString())
                current = current.next
            }
        }
    }
}

fun main() {
    println(Day23(input23.formattedInput).solvePart1(100))
    println(Day23(input23.formattedInput).solvePart2(10000000))
}