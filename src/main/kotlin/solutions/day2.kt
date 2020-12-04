package solutions

import inputs.input2

data class Policy(val string: String) {
    private val min: Int
    private val max: Int
    private val letter: Char

    init {
        val parts = string.split(" ")
        val range = parts[0].split("-").map { it.toInt() }
        this.min = range[0]
        this.max = range[1]
        this.letter = parts[1].toCharArray()[0]
    }

    fun validate(pass: String): Boolean {
        val res = pass.split(letter.toString()).size -1
        return res in min..max
    }

    fun validate2(pass: String): Boolean {
        return ((letter == pass[min - 1] && letter != pass[max - 1]) ||
                (letter == pass[max - 1] && letter != pass[min - 1]))
    }

}

fun first(input: List<List<String>>): Int {
    var res = 0
        for (i in input.indices) {
            val pol = Policy(input[i][0])
            if (pol.validate(input[i][1]))
                res++
        }

    return res;
}

fun second(input: List<List<String>>): Int {
    var res = 0
    for (i in input.indices) {
        val pol = Policy(input[i][0])
        if (pol.validate2(input[i][1]))
            res++
    }

    return res;

}

fun main() {
    println(first(input2.formattedInput))
    println(second(input2.formattedInput))
}