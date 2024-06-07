import algorithms.*
import objects.AlgoStats
import objects.Puzzle

fun main() {
    val instances = 100
    val depths = listOf(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24)

    println("Depth\tIterative Deep Search\tA* Search H1\tA* Search H2\tGreedy Search H1\tGreedy Search H2")
    for (depth in depths) {
        val iterativeDeepSearchs = MutableList(instances) { AlgoStats(null, 0) }
        val aStarSearchH1s = MutableList(instances) { AlgoStats(null, 0) }
        val aStarSearchH2s = MutableList(instances) { AlgoStats(null, 0) }
        val greedySearchH1s = MutableList(instances) { AlgoStats(null, 0) }
        val greedySearchH2s = MutableList(instances) { AlgoStats(null, 0) }

        for (i in 0..<instances) {
            val puzzle = Puzzle(depth)

            iterativeDeepSearchs[i] = iterativeDeepSearch(puzzle, depth)
            aStarSearchH1s[i] = aStarSearch(puzzle, ::heuristicH1)
            aStarSearchH2s[i] = aStarSearch(puzzle, ::heuristicH2)
            greedySearchH1s[i] = greedySearch(puzzle, ::heuristicH1)
            greedySearchH2s[i] = greedySearch(puzzle, ::heuristicH2)
        }

        val iterativeDeepSearch = iterativeDeepSearchs.map { it.nodesVisited }.average()
        val aStarSearchH1 = aStarSearchH1s.map { it.nodesVisited }.average()
        val aStarSearchH2 = aStarSearchH2s.map { it.nodesVisited }.average()
        val greedySearchH1 = greedySearchH1s.map { it.nodesVisited }.average()
        val greedySearchH2 = greedySearchH2s.map { it.nodesVisited }.average()

        println("$depth\t$iterativeDeepSearch\t$aStarSearchH1\t$aStarSearchH2\t$greedySearchH1\t$greedySearchH2")
    }


}