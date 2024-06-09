package algorithms

import objects.AlgoStats
import objects.Puzzle
import java.util.*

fun uniformCostSearch(startState: Puzzle): AlgoStats {
    val openList = PriorityQueue(compareBy<Puzzle> { it.depth })
    val closedSet = mutableSetOf<IntArray>()
    var nodes = 0

    openList.add(startState)

    while (openList.isNotEmpty()) {
        val currentState = openList.poll()
        nodes++
        if (currentState.isSolved) return AlgoStats(currentState, nodes)

        closedSet.add(currentState.matrix)

        for (nextState in currentState.nextPossibleSteps()) {
            if (nextState.matrix !in closedSet) {
                openList.add(nextState)
            }
        }
    }
    return AlgoStats(null, nodes)
}