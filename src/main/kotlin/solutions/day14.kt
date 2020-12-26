package solutions

import inputs.input14


class day14 {
    private val memory: MutableMap<Long, Long> = mutableMapOf()

    companion object {
        const val DEFAULT_MASK = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
    }

    private fun String.toBinary(): String =
            this.toLong().toString(2).padStart(36, '0')

    private infix fun String.maskedWith(mask: String): Long =
            this.toBinary().zip(mask).map { (valueChar, maskChar) ->
                maskChar.takeUnless { it == 'X' } ?: valueChar
            }.joinToString("").toLong(2)

    fun solvePart1(input: List<String> ): Long {
        var mask = DEFAULT_MASK
        input.forEach { instruction ->
            if (instruction.startsWith("mask")) {
                mask = instruction.substringAfter("= ")
            } else {
                val address = instruction.substringAfter("[").substringBefore("]").toLong()
                val value = instruction.substringAfter("= ")
                memory[address] = value maskedWith mask
            }
        }

        return memory.values.sum()
    }

    private fun String.generateAddressMasks(mask: String): List<Long> {
        val addresses = mutableListOf(this.toBinary().toCharArray())
        mask.forEachIndexed { idx, bit ->
            when (bit) {
                '1' -> addresses.forEach { it[idx] = '1' }
                'X' -> {
                    addresses.forEach { it[idx] = '1' }
                    addresses.addAll(
                            addresses.map {
                                it.copyOf().apply {
                                    this[idx] = '0'
                                }
                            }
                    )
                }
            }
        }
        return addresses.map { it.joinToString("").toLong(2) }

    }

    fun solvePart2(input: List<String>): Long {
        var mask = DEFAULT_MASK
        input.forEach { instruction ->
            if (instruction.startsWith("mask")) {
                mask = instruction.substringAfter("= ")
            } else {
                val unmaskedAddress = instruction.substringAfter("[").substringBefore("]")
                val value = instruction.substringAfter("= ").toLong()
                unmaskedAddress.generateAddressMasks(mask).forEach { address ->
                    memory[address] = value
                }

            }
        }
        return memory.values.sum()
    }

}


private fun first(input : List<String>) : Long {
    return day14().solvePart1(input)
}

private fun second(input: List<String>) : Long {
    return day14().solvePart2(input)
}

fun main() {
    println(first(input14.formattedInput))
    println(second(input14.formattedInput))
}