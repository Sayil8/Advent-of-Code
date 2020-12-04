package solutions

import inputs.input

private fun first(input: List<Int>): Int {
    for(i in input.indices)
        for(j in input.indices)
            if(input[i] + input[j] == 2020) {
                return input[i] * input[j]
            }

    return 0
}

private fun second(input: List<Int>): Int {
    for(i in input.indices)
        for (j in input.indices)
            for (k in input.indices)
                if(input[i] + input[j] + input[k] == 2020)
                    return input[i] * input[j] * input[k]
    return 0
}

fun main() {
    println(first(input.formattedInput))
    println(second(input.formattedInput))
}