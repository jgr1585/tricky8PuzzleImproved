import algorithms.*
import de.m3y.kformat.Table
import de.m3y.kformat.table
import kotlinx.coroutines.*
import objects.AlgoStats
import objects.Puzzle
import kotlin.concurrent.thread


@OptIn(DelicateCoroutinesApi::class)
suspend fun main() {
    val instances = 100
    val depths = listOf(2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24)


    table {
        header(
            "Depth",
            "Iterative Deep Search",
            "A* Search H1",
            "A* Search H2",
            "A* Search H3",
            "Greedy Search H1",
            "Greedy Search H2",
            "Greedy Search H3",
            "Uniform Cost Search"
        )

        hints {
            borderStyle = Table.BorderStyle.SINGLE_LINE
            (1..9).forEach { precision(it, 2) } // Set all columns to have 2 decimal places
        }

        for (depth in depths) {
            print("Now running depth $depth...")

            val iterativeDeepSearchs = MutableList(instances) { AlgoStats(null, 0) }
            val aStarSearchH1s = MutableList(instances) { AlgoStats(null, 0) }
            val aStarSearchH2s = MutableList(instances) { AlgoStats(null, 0) }
            val aStarSearchH3s = MutableList(instances) { AlgoStats(null, 0) }
            val greedySearchH1s = MutableList(instances) { AlgoStats(null, 0) }
            val greedySearchH2s = MutableList(instances) { AlgoStats(null, 0) }
            val greedySearchH3s = MutableList(instances) { AlgoStats(null, 0) }
            val uniformCostSearches = MutableList(instances) { AlgoStats(null, 0) }

            // Launch multiple coroutines to run the algorithms in parallel
            runBlocking {
                val jobs = List(instances) { i ->
                    launch(newFixedThreadPoolContext(4, "MyPool")) {
                        val puzzle = Puzzle(depth)

                        thread { iterativeDeepSearchs[i] = iterativeDeepSearch(puzzle, depth) }
                        thread { aStarSearchH1s[i] = aStarSearch(puzzle, ::heuristicH1) }
                        thread { aStarSearchH2s[i] = aStarSearch(puzzle, ::heuristicH2) }
                        thread { aStarSearchH3s[i] = aStarSearch(puzzle, ::heuristicH3) }
                        thread { greedySearchH1s[i] = greedySearch(puzzle, ::heuristicH1) }
                        thread { greedySearchH2s[i] = greedySearch(puzzle, ::heuristicH2) }
                        thread { greedySearchH3s[i] = greedySearch(puzzle, ::heuristicH3) }
                        thread { uniformCostSearches[i] = uniformCostSearch(puzzle) }
                    }
                }

                jobs.forEach { it.join() }
                print("Done!\n")
            }

            row(
                depth,
                iterativeDeepSearchs.map { it.nodesVisited }.average(),
                aStarSearchH1s.map { it.nodesVisited }.average(),
                aStarSearchH2s.map { it.nodesVisited }.average(),
                aStarSearchH3s.map { it.nodesVisited }.average(),
                greedySearchH1s.map { it.nodesVisited }.average(),
                greedySearchH2s.map { it.nodesVisited }.average(),
                greedySearchH3s.map { it.nodesVisited }.average(),
                uniformCostSearches.map { it.nodesVisited }.average()
            )
        }
    }.render(StringBuilder()).toString().let(::println)

}