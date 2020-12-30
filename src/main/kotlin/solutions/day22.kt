package solutions

import inputs.input22
import java.util.*

fun first(input: String) : Int {
    var playerOneCarts : MutableList<Int> = input.substringAfter("Player 1:")
                                  .substringBefore("Player 2:")
                                  .trim()
                                  .split("\n")
                                  .map { it.toInt() }
                                  .toMutableList()

    var playerTwoCarts :  MutableList<Int> = input.substringAfter("Player 2:")
            .trim()
            .split("\n")
            .map { it.toInt() }
            .toMutableList()

    while (playerOneCarts.isNotEmpty() && playerTwoCarts.isNotEmpty()) {
            if (playerOneCarts[0] > playerTwoCarts[0]) {
                playerOneCarts.add(playerOneCarts[0])
                playerOneCarts.add(playerTwoCarts[0])
            } else {
                playerTwoCarts.add(playerTwoCarts[0])
                playerTwoCarts.add(playerOneCarts[0])
            }
        playerOneCarts.remove(playerOneCarts[0])
        playerTwoCarts.remove(playerTwoCarts[0])
    }

    val winnerCarts = if (playerOneCarts.isEmpty()) playerTwoCarts else playerOneCarts
    var result = 0
    for (i in 1..winnerCarts.size) {
        result += winnerCarts[i - 1] * (winnerCarts.size - i + 1)
    }
    return result
}

class Day22(input: String) {
    private var playerOneCarts : MutableList<Int> = input.substringAfter("Player 1:")
            .substringBefore("Player 2:")
            .trim()
            .split("\n")
            .map { it.toInt() }
            .toMutableList()

    private var playerTwoCarts :  MutableList<Int> = input.substringAfter("Player 2:")
            .trim()
            .split("\n")
            .map { it.toInt() }
            .toMutableList()

    private fun MutableList<Int>.pairedWith(other: MutableList<Int>): Sequence<Pair<Int, Int>> = sequence {
        while (this@pairedWith.isNotEmpty() && other.isNotEmpty()) {
            yield(Pair(this@pairedWith.removeFirst(), other.removeFirst()))
        }
    }
    
    private fun MutableList<Int>.score(): Int =
            this.foldIndexed(0) { index, acc, current -> acc + (current * (size - index)) }

    private fun playGame(deckA: MutableList<Int>, deckB: MutableList<Int>, allowRecursive: Boolean): Boolean {
        val history = mutableSetOf(Objects.hash(deckA, deckB))
        deckA.pairedWith(deckB).forEach { (a, b) ->
            val winner = when {
                allowRecursive && deckA.size >= a && deckB.size >= b ->
                    playGame(
                            deckA.take(a).toMutableList(),
                            deckB.take(b).toMutableList(),
                            true
                    )
                else -> a > b
            }
            if (winner) deckA.addAll(listOf(a, b)) else deckB.addAll(listOf(b, a))

            if(allowRecursive) {
                Objects.hash(deckA, deckB).also {
                    if (it in history) return true else history += it
                }
            }
        }
        return deckA.isNotEmpty()
    }

    private fun playUntilWinner(allowRecursive: Boolean = false): MutableList<Int> =
            if (playGame(playerOneCarts, playerTwoCarts, allowRecursive)) playerOneCarts else playerTwoCarts

    fun solvePart2(): Int =
            playUntilWinner(true).score()

}
private fun second(input: String) : Int {
    return Day22(input).solvePart2()
}

fun main() {
    println(first(input22.formattedInput))
    println(second(input22.formattedInput))
}
