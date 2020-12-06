package solutions

import inputs.input6

private fun countAnyoneQuestion(string: List<String>) : Int {
    var total = 0
    var answers: Set<String> = setOf()

    for (char in string) {
        for (letter in char) {
            if (!answers.contains(letter.toString()))
                total ++
            answers += letter.toString()
        }
    }

    return total
}

private fun countEveryoneQuestion(strings: List<String>) : Int {

    var total = 0
    var answers: Set<String> = setOf()
    var flag: Boolean

    for (string in strings) {
        for (char in string) {
            flag = true
            if (!answers.contains(char.toString())) {
                for (string in strings) {
                    if (!string.contains(char)) {
                        flag = false
                        break
                    }
                }
                if (flag) total++
                answers += char.toString()
            }
        }
    }


    return total
}

private fun first(answers: List<String>) : Int {
    var count = 0

    for (answer in answers) {
        count += countAnyoneQuestion(answer.trimIndent().split("\n"))
    }

    return count
}

private fun second(answers: List<String>) : Int {
    var count = 0

    for (answer in answers) {
        count += countEveryoneQuestion(answer.trimIndent().split("\n"))
    }

    return count
}

fun main() {
    println(first(input6.formattedInput))
    println(second(input6.formattedInput))
}