package objects

class Puzzle(
    private val matrix: IntArray = IntArray(9) { it + 1 }.apply {
        this[8] = 0
        shuffle()
    }
) {


    val isSolved: Boolean
        get() = matrix.contentEquals(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 0))

    fun getMatrixAsString(): String {
        return (0..<3).joinToString("\n") { i ->
            (0..<3).joinToString(" ") { j ->
                matrix[i * 3 + j].toString()
            }
        } + "\n"
    }

    fun nextPosibleSteps(): List<Puzzle> {
        val zeroIndex = matrix.indexOf(0)

        return listOfNotNull(
            if (zeroIndex > 2) zeroIndex - 3 else null, // Move zero up
            if (zeroIndex < 6) zeroIndex + 3 else null, // Move zero down
            if (zeroIndex % 3 > 0) zeroIndex - 1 else null, // Move zero left
            if (zeroIndex % 3 < 2) zeroIndex + 1 else null // Move zero right
            ).map {
                swapPeaces(zeroIndex, it)
            }
    }

    private fun swapPeaces(i: Int, j: Int): Puzzle {
        return Puzzle(matrix.clone().apply {
            this[i] = this[j].also { this[j] = this[i] }
        })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Puzzle) return false

        if (!matrix.contentEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentHashCode()
    }


}