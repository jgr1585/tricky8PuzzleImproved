package algorithms

import objects.AlgoStats
import objects.Puzzle

fun aStarSearch(startState: Puzzle, heuristic: (Puzzle) -> Int): AlgoStats {
    val openList = mutableListOf(startState)
    val closedList = mutableSetOf<Puzzle>()

    while (openList.isNotEmpty()) {
        val current = openList.minBy { it.depth + heuristic(it) }

        if (current.isSolved) {
            return AlgoStats(current, closedList.size)
        }

        openList.remove(current)
        closedList.add(current)

        for (successor in current.nextPossibleSteps()) {
            if (successor !in closedList) {
                openList.add(successor)
            }
        }
    }

    return AlgoStats(null, closedList.size)
}