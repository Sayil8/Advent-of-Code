package inputs

object input15 {
    val formattedInput = """
19,20,14,0,9,1
""".trimIndent().split(",").map { it.toInt()}.toMutableList()
}