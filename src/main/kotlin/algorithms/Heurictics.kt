package algorithms

import objects.Puzzle
import kotlin.math.abs

fun heuristicH1(state: Puzzle): Int {
    return state.matrix
        .withIndex()
        .count { (index, value) -> value != 0 && value != index + 1 }
}

fun heuristicH2(state: Puzzle): Int {
    return state.matrix
        .withIndex()
        .sumOf { (index, value) ->
            if (value != 0)
                abs(index / 3 - (value - 1) / 3) + abs(index % 3 - (value - 1) % 3)
            else 0
        }
}

fun heuristicH3(state: Puzzle): Int {
    return state.matrix
        .withIndex()
        .map { (index, value) -> if (value != 0 && value != index + 1) 1 else 0 }
        .sum()
}