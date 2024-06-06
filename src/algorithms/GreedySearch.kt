package algorithms

import objects.AlgoStats
import objects.Puzzle

fun greedySearch(start: Puzzle, heuristic: (Puzzle) -> Int): AlgoStats {
    val openList = mutableListOf(start)
    val closedList = mutableSetOf<Puzzle>()

    while (openList.isNotEmpty()) {
        val current = openList.minBy { heuristic(it) }

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