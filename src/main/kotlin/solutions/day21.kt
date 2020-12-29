package solutions

import inputs.input21

class day21(input : List<String>) {

    fun parseInput(input: List<String>) : Map<Set<String>, Set<String>> =
        input.map {
            val ingredients = it.substringBefore(" (").split(" ").toSet()
            val allergens = it.substringAfter("(contains").substringBefore(")").split(",").toSet()
            ingredients to allergens
        }.toMap()

    val food : Map<Set<String>, Set<String>> = parseInput(input)
    val allIngredients = food.keys.flatten().toSet()
    val allAllergies : Set<String> = food.values.flatten().toSet()

    fun safeIngredients(): Set<String> =
            allIngredients subtract allAllergies.flatMap { allergy ->
                food
                        .filter { allergy in it.value }
                        .map { it.key }
                        .reduce { carry, ingredients -> ingredients intersect carry }
            }.toSet()

    private fun ingredientsByAllergy(): MutableMap<String, MutableSet<String>> {
        val safeIngredients = safeIngredients()

        return allAllergies.map { allergy ->
            allergy to food.entries
                    .filter { allergy in it.value }
                    .map { it.key - safeIngredients }
                    .reduce { a, b -> a intersect b }
                    .toMutableSet()
        }.toMap().toMutableMap()
    }

    fun solvePart2(): String {
        val ingredientsByAllergy = ingredientsByAllergy()
        val found: MutableMap<String, String> = mutableMapOf()

        while (ingredientsByAllergy.isNotEmpty()) {
            val singles = ingredientsByAllergy
                    .filter { it.value.size == 1 }
                    .map { it.key to it.value.first() }
                    .toMap()
            found.putAll(singles)
            singles.keys.forEach { ingredientsByAllergy.remove(it) }
            ingredientsByAllergy.values.forEach { it.removeAll(singles.values) }
        }

        return found.entries.sortedBy { it.key }.joinToString(",") { it.value }
    }
}

private fun first(input: List<String>) : Int {
        val safeIngredients = day21(input).safeIngredients()
        return day21(input).food.keys.sumOf { recipe ->
            recipe.count { it in safeIngredients }
        }

}

private fun second(input: List<String>) : String =
        day21(input).solvePart2()


fun main() {
    println(first(input21.formattedInput))
    println(second(input21.formattedInput))

}