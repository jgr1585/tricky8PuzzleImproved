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

    var result = AlgoStats(null, nodesVisited)
    for (successor in state.nextPossibleSteps()) {
        result = depthLimitedSearch(successor, limit, result.nodesVisited + 1)
        if (result.result != null) { return result }
    }

    return AlgoStats(null, nodesVisited)
}