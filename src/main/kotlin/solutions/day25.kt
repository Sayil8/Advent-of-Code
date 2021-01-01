package solutions

import inputs.input25

class day25(input : List<String>) {
    private val cardPk = input.first().toLong()
    private val doorPk = input.last().toLong()

    private fun Long.mathPart(subject: Long = 7): Long =
            this * subject % 20201227

    private fun findLoopSize(target: Long): Int =
            generateSequence(1L) { it.mathPart() }.indexOf(target)

    private fun transform(loopSize: Int, subject: Long): Long =
            generateSequence(1L) { it.mathPart(subject) }.drop(loopSize).first()

    fun solvePart1(): Long =
            transform(findLoopSize(cardPk), doorPk)

}

private fun first(input: List<String>)  {
    println(day25(input).solvePart1())
}

fun main() {
    first(input25.formattedInput)
}