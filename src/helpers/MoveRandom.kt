package helpers

fun IntArray.moveRandom() {
    val zeroIndex = this.indexOf(0)
    val possibleMoves = listOfNotNull(
        if (zeroIndex > 2) zeroIndex - 3 else null, // Move zero up
        if (zeroIndex < 6) zeroIndex + 3 else null, // Move zero down
        if (zeroIndex % 3 > 0) zeroIndex - 1 else null, // Move zero left
        if (zeroIndex % 3 < 2) zeroIndex + 1 else null // Move zero right
    )

    val randomIndex = possibleMoves.random()
    this[zeroIndex] = this[randomIndex].also { this[randomIndex] = this[zeroIndex] } // Swap values of clone

}