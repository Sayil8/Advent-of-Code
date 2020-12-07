package solutions

import inputs.input7

private fun search(rules: List<String>, bag_color: String, bags: Set<String>, passed_bags: Set<String>) : Set<String> {
    var new_bags = bags;
    var passed_bags = passed_bags;
    var new_bag_color : String;
    if (!passed_bags.contains(bag_color)) {
        passed_bags+= bag_color
        for (rule in rules) {
            if (rule.contains(bag_color) && !rule.startsWith(bag_color)) {
                new_bag_color = rule.substringBefore(" bags")
                new_bags+= new_bag_color
                new_bags+= search(rules, new_bag_color, new_bags, passed_bags)
            }
        }
    }

    return new_bags
}

private fun count_bags(rules: List<String>, bag_color: String, total: Int, passed_colors: Set<String>): Int {
    var new_total = total
    var pased_colors = passed_colors

    for (rule in rules) {
        if(rule.startsWith(bag_color) && !pased_colors.contains(bag_color)){
            pased_colors += bag_color
            val parts = rule.substringAfter("contain").trimIndent().split(",")
            parts.forEach{
                val num_bags = it.filter { it.isDigit() }.toIntOrNull()
                if (num_bags == null){
                    return 0
                }
                val new_bag_color = it.substringBefore("bag").replace("[0-9]".toRegex(), "").trim()
                new_total += num_bags!! + (num_bags * count_bags(rules, new_bag_color, total, passed_colors))
            }
        }

    }

    return new_total
}

private fun first(rules: List<String>): Int {
    var bags = setOf<String>()
    var passed_bags = setOf<String>()
    val bag_color = "shiny gold"

    bags = search(rules, bag_color, bags, passed_bags)

    return bags.size;
}

private fun second(rules: List<String>): Int {
    var passed_colors = setOf<String>()
    val bag_color = "shiny gold"

    val total = count_bags(rules, bag_color, 0, passed_colors)

    return total
}

fun main() {
    println(first(input7.formattedInput))
    println(second(input7.formattedInput))
}