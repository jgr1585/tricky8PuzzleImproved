import algorithms.*
import objects.AlgoStats
import objects.Puzzle
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


suspend fun main() {
    val instances = 100
    val depths = listOf(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24)

    println("Depth\tIterative Deep Search\tA* Search H1\tA* Search H2\tA* Search H3\tGreedy Search H1\tGreedy Search H2\tGreedy Search H3")
    for (depth in depths) {
        val iterativeDeepSearchs = MutableList(instances) { AlgoStats(null, 0) }
        val aStarSearchH1s = MutableList(instances) { AlgoStats(null, 0) }
        val aStarSearchH2s = MutableList(instances) { AlgoStats(null, 0) }
        val aStarSearchH3s = MutableList(instances) { AlgoStats(null, 0) }
        val greedySearchH1s = MutableList(instances) { AlgoStats(null, 0) }
        val greedySearchH2s = MutableList(instances) { AlgoStats(null, 0) }
        val greedySearchH3s = MutableList(instances) { AlgoStats(null, 0) }

        val jobs = List(instances) { i ->
            runBlocking {
                launch {
                    val puzzle = Puzzle(depth)

                    iterativeDeepSearchs[i] = iterativeDeepSearch(puzzle, depth)
                    aStarSearchH1s[i] = aStarSearch(puzzle, ::heuristicH1)
                    aStarSearchH2s[i] = aStarSearch(puzzle, ::heuristicH2)
                    aStarSearchH3s[i] = aStarSearch(puzzle, ::heuristicH3)
                    greedySearchH1s[i] = greedySearch(puzzle, ::heuristicH1)
                    greedySearchH2s[i] = greedySearch(puzzle, ::heuristicH2)
                    greedySearchH3s[i] = greedySearch(puzzle, ::heuristicH3)
                }
            }
        }

        jobs.forEach { it.join() }

        val iterativeDeepSearch = String.format("%.2f", iterativeDeepSearchs.map { it.nodesVisited }.average())
        val aStarSearchH1 = String.format("%.2f", aStarSearchH1s.map { it.nodesVisited }.average())
        val aStarSearchH2 = String.format("%.2f", aStarSearchH2s.map { it.nodesVisited }.average())
        val aStarSearchH3 = String.format("%.2f", aStarSearchH3s.map { it.nodesVisited }.average())
        val greedySearchH1 = String.format("%.2f", greedySearchH1s.map { it.nodesVisited }.average())
        val greedySearchH2 = String.format("%.2f", greedySearchH2s.map { it.nodesVisited }.average())
        val greedySearchH3 = String.format("%.2f", greedySearchH3s.map { it.nodesVisited }.average())

        println("$depth\t$iterativeDeepSearch\t$aStarSearchH1\t$aStarSearchH2\t$aStarSearchH3\t$greedySearchH1\t$greedySearchH2\t$greedySearchH3")
    }


}