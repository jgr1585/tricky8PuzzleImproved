package algorithms

import objects.AlgoStats
import objects.Puzzle

fun iterativeDeepSearch(start: Puzzle, maxDepth: Int): AlgoStats {
    var result = AlgoStats(null, 0)
    for (i in 0..maxDepth) {
        result = depthLimitedSearch(start, i, result.nodesVisited)
        if (result.result != null) { return result }
    }
    return result
}

fun depthLimitedSearch(state: Puzzle, limit: Int, nodesVisited: Int = 0): AlgoStats {
    if (state.isSolved) { return AlgoStats(state, nodesVisited) }
    if (state.depth >= limit) { return AlgoStats(null, nodesVisited) }

    var nodesVied = nodesVisited
    for (successor in state.nextPossibleSteps()) {
        val result = depthLimitedSearch(successor, limit, nodesVied + 1)
        if (result.result != null) { return result }

        nodesVied = result.nodesVisited
    }

    return AlgoStats(null, nodesVied)
}