package inputs

import inputs.input8

class Line(input: String) {
    val operation: String
    val argument: Int

    init {
        val instruction = input.chunked(4)
        operation = instruction[0].trim()
        argument = instruction[1].trim().toInt()
    }
}

fun first(instructions: List<String>): MutableList<Int> {
    val result = mutableListOf(0 , 0)
    var operation: Line
    val passed_operations = mutableSetOf<Int>()
    var i = 0

    while (i < instructions.size) {
        if (i == instructions.size-1) {
            result[1] = 1
            break
        }
        if (passed_operations.contains(i)) {
            break
        }
        passed_operations += i
        operation = Line(instructions[i])
        when {
            operation.operation == "acc" -> {
                result[0] += operation.argument
                i++
            }
            operation.operation == "jmp" -> {
                i += operation.argument
            }
            operation.operation == "nop" -> {
                i++
                continue
            }
        }
    }

    return result
}

fun second(instructions: List<String>): Int {
    var new_instructions = instructions.toMutableList()
    var result: MutableList<Int>
    var tried_lines = setOf<Int>()
    var i = 0

    result = first(new_instructions)
    while (result[1] != 1) {
        while (i < instructions.size) {
           var operation = Line(instructions[i])
           when {
               tried_lines.contains(i) -> {
                   i++
                   continue
               }
               operation.operation == "nop" -> {
                   new_instructions[i] = instructions[i].replace("nop", "jmp")
                   tried_lines += i
                   break
               }
               operation.operation == "jmp" -> {
                   new_instructions[i] = instructions[i].replace("jmp", "nop")
                   tried_lines += i
                   break
               }
           }
           i++
        }
        result = first(new_instructions)
        new_instructions = instructions.toMutableList()
    }

    return result[0]
}

fun main() {
    println(first(input8.formattedInput).first())
    println(second(input8.formattedInput))
}