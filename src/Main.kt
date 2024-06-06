import algorithms.*
import objects.Algo
import objects.AlgoResults
import objects.Puzzle

fun main() {
    val instances = 100
    val depths = listOf(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24)

    val results = mutableListOf<AlgoResults>()

    for (depth in depths) {
        val puzzle = Puzzle(depth)

        results.add(AlgoResults(
            Algo.ITERATIVE_DEEP_SEARCH,
            depth,
            iterativeDeepSearch(puzzle, depth)

        ))

        results.add(AlgoResults(
            Algo.A_STAR_SEARCH_H1,
            depth,
            aStarSearch(puzzle, ::heuristicH1)
        ))

        results.add(AlgoResults(
            Algo.A_STAR_SEARCH_H2,
            depth,
            aStarSearch(puzzle, ::heuristicH2)
        ))

        results.add(AlgoResults(
            Algo.GREEDY_SEARCH_H1,
            depth,
            greedySearch(puzzle, ::heuristicH1)
        ))

        results.add(AlgoResults(
            Algo.GREEDY_SEARCH_H2,
            depth,
            greedySearch(puzzle, ::heuristicH2)
        ))
    }

    println("Depth\tIterative Deep Search\tA* Search H1\tA* Search H2\tGreedy Search H1\tGreedy Search H2")
    for (depth in depths) {
        val iterativeDeepSearch = results.find { it.depth == depth && it.algo == Algo.ITERATIVE_DEEP_SEARCH }!!.stats
        val aStarSearchH1 = results.find { it.depth == depth && it.algo == Algo.A_STAR_SEARCH_H1 }!!.stats
        val aStarSearchH2 = results.find { it.depth == depth && it.algo == Algo.A_STAR_SEARCH_H2 }!!.stats
        val greedySearchH1 = results.find { it.depth == depth && it.algo == Algo.GREEDY_SEARCH_H1 }!!.stats
        val greedySearchH2 = results.find { it.depth == depth && it.algo == Algo.GREEDY_SEARCH_H2 }!!.stats

        println("$depth\t${iterativeDeepSearch.nodesVisited}\t${aStarSearchH1.nodesVisited}\t${aStarSearchH2.nodesVisited}\t${greedySearchH1.nodesVisited}\t${greedySearchH2.nodesVisited}")
    }


}