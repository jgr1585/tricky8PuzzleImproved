import objects.Puzzle

fun main() {
    println("Hello World!")

    val puzzle = Puzzle()
    val openList = mutableListOf(puzzle)
    val closeList = mutableSetOf(puzzle)

    while (openList.isNotEmpty()) {
        val current = openList.removeFirst()
        println(current.getMatrixAsString())
        println("Is final state: ${current.isSolved}")
        println("----")

        if (current.isSolved) {
            break
        }

        current.nextPossibleSteps().forEach { successor ->
            if (!closeList.contains(successor)) {
                closeList.add(successor)
                openList.add(successor)
            }
        }
    }

}