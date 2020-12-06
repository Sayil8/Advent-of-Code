package solutions
 import inputs.input5

private fun seatId(string: String): Int {
    var row = 0..127
    var col = 0..7

    for (char in string) {
        when (char.toString()) {
            "F" -> {
                val size = row.last - row.first
                row = row.first..( row.first + size / 2)
            }
            "B" -> {
                val size = row.last - row.first
                row = (row.first + size / 2 + 1)..row.last
            }
            "L" -> {
                val size = col.last - col.first
                col = col.first..(col.first + size / 2)
            }
            "R" -> {
                val size = col.last - col.first
                col = (col.first + size / 2 + 1)..col.last
            }
            else -> return -1
        }
    }
    return row.first * 8 + col.first
}

private fun first(sequences: List<String>): Int {
    var highest = 0

    for(sequence in sequences) {
        var current = seatId(sequence)
        if(current > highest)
            highest = current
    }

    return highest
}

private fun second(sequences: List<String>): Int {
    var seatIds: Set<Int> = setOf()

    for(sequence in sequences){
        seatIds += seatId(sequence)
    }

    var mySeat = 0
    val sortedSeats = seatIds.sorted()

    for (seat in 0 until (sortedSeats.last() - sortedSeats.first())) {
        if (sortedSeats[seat] + 2 == sortedSeats[seat + 1]) {
            mySeat = sortedSeats[seat] + 1
            break
        }
    }

    return mySeat;
}

fun main() {
    println(first(input5.formattedInput))
    println(second(input5.formattedInput))

}