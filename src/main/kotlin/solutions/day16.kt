package solutions

import inputs.input16

class Day16() {

    fun parseTickets(input: List<String>) : Map<String,List<IntRange>> {
        return input.map {
             val name = it.substringBefore(":")
             val firstRange = it.substringBefore(" or").substringAfter(": ")
             val secondRange = it.substringAfter("or ")
             name to listOf(
                     firstRange.substringBefore("-").toInt() .. firstRange.substringAfter("-").toInt(),
                     secondRange.substringBefore("-").toInt() .. secondRange.substringAfter("-").toInt()
             )
         }.toMap()
    }

    fun parseOwnTicket(input: List<String>) : List<Int> =
            input.drop(1).first().split(",").map { it.toInt() }


    fun parseNearbyTickets(input: List<String>) : List<List<Int>> =
            input.drop(1).map { it.split(",").map { it.toInt() } }

}


private fun first(input: List<String>) : Int {

    val ticketRules : Map<String, List<IntRange>> = Day16().parseTickets(input.first().split("\n"))
    val allRules : List<IntRange> = ticketRules.values.flatten()
    val nearbyTickets : List<List<Int>> = Day16().parseNearbyTickets(input.last().split("\n"))

    return nearbyTickets.sumBy {
        it.filter { field ->
            allRules.none{ rule -> field in rule }
        }.sum()
    }
}

private fun second(input: List<String>) : Long{
    val ticketRules : Map<String, List<IntRange>> = Day16().parseTickets(input.first().split("\n"))
    val allRules : List<IntRange> = ticketRules.values.flatten()
    val ourTicket: List<Int> = Day16().parseOwnTicket(input[1].split("\n"))
    val nearbyTickets : List<List<Int>> = Day16().parseNearbyTickets(input.last().split("\n"))

    fun List<Int>.isValidTicket(): Boolean =
            this.all { field ->
                allRules.any { rule ->
                    field in rule
                }
            }
    fun List<List<Int>>.columnPassesRule(column: Int, fieldName: String): Boolean =
            this.all { ticket ->
                ticketRules.getValue(fieldName).any { rule -> ticket[column] in rule }
            }

    fun reduceRules(possibleRules: Map<String,MutableSet<Int>>): Map<String,Int> {
        val foundRules = mutableMapOf<String,Int>()
        while(foundRules.size < possibleRules.size) {
            possibleRules.entries
                    .filter { (_, possibleValues) -> possibleValues.size == 1 }
                    .forEach { (rule, possibleValues) ->
                        val columnNumber = possibleValues.first()
                        foundRules[rule] = columnNumber
                        possibleRules.values.forEach { it.remove(columnNumber) }
                    }
        }
        return foundRules
    }

    val validTickets = nearbyTickets.filter { it.isValidTicket() }

    val possibleFieldRules: Map<String,MutableSet<Int>> = ticketRules.keys.map { rule ->
        rule to ourTicket.indices.filter { column ->
            validTickets.columnPassesRule(column, rule)
        }.toMutableSet()
    }.toMap()

    val foundRules = reduceRules(possibleFieldRules)

    return foundRules.entries
            .filter { it.key.startsWith("departure") }
            .map { ourTicket[it.value].toLong() }
            .reduce { a, b -> a * b }

}


fun main() {
    println(first(input16.formattedInput))
    println(second(input16.formattedInput))
}