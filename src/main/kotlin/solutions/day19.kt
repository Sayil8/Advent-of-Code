package solutions

import inputs.input19

class day19(input: List<String>) {

    interface Rule
    class Atom(val symbol: Char) : Rule
    class RuleReference(val id: Int) : Rule

    private fun parseRules(input: List<String>): MutableMap<Int, List<List<Rule>>> =
            input.takeWhile{ it.isNotBlank() }.map { line ->
                val (id, rhs) = line.split(": ")
                val sides = rhs.split(" | ")
                id.toInt() to sides.map { side ->
                    side.split(' ').map { part ->
                        if (part.startsWith('"')) Atom(part[1])
                        else RuleReference(part.toInt())
                    }
                }
            }.toMap().toMutableMap()

    private val rules: MutableMap<Int, List<List<Rule>>> = parseRules(input)
    private val messages: List<String> = input.dropWhile { it.isNotBlank() }.drop(1)

    private fun String.ruleMatch(ruleId: Int, position: Int = 0): List<Int> =
            rules.getValue(ruleId).flatMap { listOfRules -> // OR Rule
                var positions = listOf(position)
                listOfRules.forEach { rule ->  // AND Rule
                    positions = positions.mapNotNull { idx ->
                        when {
                            rule is Atom && getOrNull(idx) == rule.symbol ->
                                listOf(idx + 1) // End condition
                            rule is RuleReference ->
                                ruleMatch(rule.id, idx)
                            else ->
                                null
                        }
                    }.flatten()
                }
                positions
            }

    fun countRuleMatches(): Int =
            messages.count { message ->
                message.ruleMatch(0).any { it == message.length }
            }

    fun solvePart2(): Int {
        rules[8] = listOf(
                listOf(RuleReference(42)),
                listOf(RuleReference(42), RuleReference(8))
        )
        rules[11] = listOf(
                listOf(RuleReference(42), RuleReference(31)),
                listOf(RuleReference(42), RuleReference(11), RuleReference(31))
        )
        return countRuleMatches()
    }
}

private fun first(input: List<String>): Int =
        day19(input).countRuleMatches()

private fun second(input: List<String>) : Int =
        day19(input).solvePart2()

fun main() {
    println(first(input19.formattedInput))
    println(second(input19.formattedInput))
}