package objects

import helpers.moveRandom

class Puzzle(
    val matrix: IntArray,
    val parent: Puzzle? = null,
    val depth: Int = 0,
) {

    constructor(shuffleCount: Int) : this(IntArray(9) { it + 1 }.apply {
        this[8] = 0 // Set last element to 0 instead of 9
        repeat(shuffleCount) {
            moveRandom() // Shuffle 100 times
        }
    })


    val isSolved: Boolean
        get() = matrix.contentEquals(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 0))

    fun nextPossibleSteps(): List<Puzzle> {
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

    fun printBoard() {
        for (i in matrix.indices step 3) {
            println(matrix.slice(i..(i + 2)).joinToString(" "))
        }
    }

    private fun swapPeaces(i: Int, j: Int): Puzzle {
        return Puzzle(matrix.clone().apply {
            this[i] = this[j].also { this[j] = this[i] } // Swap values of clone
        }, this, depth + 1)
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