package solutions

import inputs.input18

private fun first(input : List<String>) : Long {
    val equations = input.map { it.replace(" ", "") }

    fun Char.asLong() : Long =
        this.toString().toLong()

    fun solvePart1(equation: CharIterator): Long {
        val numbers = mutableListOf<Long>()
        var op = '+'
        while (equation.hasNext()) {
            when (val next = equation.nextChar()) {
                '(' -> numbers += solvePart1(equation)
                ')' -> break
                in setOf('+', '*') -> op = next
                else -> numbers += next.asLong()
            }
            if (numbers.size == 2) {
                val a = numbers.removeLast()
                val b = numbers.removeLast()
                numbers += if (op == '+') a + b else a * b
            }
        }
        return numbers.first()
    }

    return equations.sumOf { solvePart1(it.iterator()) }

}

private fun second(input: List<String>) : Long{
    val equations = input.map { it.replace(" ", "") }
    fun Iterable<Long>.product(): Long =
            this.reduce { a, b -> a * b }

    fun Char.asLong() : Long =
            this.toString().toLong()

    fun solvePart2(equation: CharIterator): Long {
        val multiplyThese = mutableListOf<Long>()
        var added = 0L
        while (equation.hasNext()) {
            val next = equation.nextChar()
            when {
                next == '(' -> added += solvePart2(equation)
                next == ')' -> break
                next == '*' -> {
                    multiplyThese += added
                    added = 0L
                }
                next.isDigit() -> added += next.asLong()
            }
        }
        return (multiplyThese + added).product()
    }

    return equations.sumOf { solvePart2(it.iterator()) }
}

fun main() {
    println(first(input18.formattedInput))
    println(second(input18.formattedInput))

}