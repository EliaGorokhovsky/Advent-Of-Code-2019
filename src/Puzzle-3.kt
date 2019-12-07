import java.io.File
import kotlin.math.abs
import kotlin.math.min

fun main() {
    val input = File("src/inputs/3.in").readLines()
    val wire1Moves = input[0].split(",")
    val wire2Moves = input[1].split(",")
    val intersects = mutableListOf<Pair<Int, Int>>()
    val grid = Array(20000) { Array(20000) { 0 } }
    val pos = mutableListOf(grid.size / 2, grid.size / 2)
    var wire1Locations = mutableListOf(Pair(0, 0))
    var wire2Locations = mutableListOf(Pair(0, 0))
    wire1Moves.forEach {
        val dir = it[0]
        val dist = it.slice(1 until it.length).toInt()
        for (i in 0 until dist) {
            if (dir == 'U') {
                pos[1] += 1
            } else if (dir == 'D') {
                pos[1] -= 1
            } else if (dir == 'R') {
                pos[0] += 1
            } else {
                pos[0] -= 1
            }
            wire1Locations.add(Pair(pos[0], pos[1]))
            grid[pos[0]][pos[1]] = 1
        }
    }
    pos[0] = grid.size / 2
    pos[1] = grid.size / 2
    wire2Moves.forEach {
        val dir = it[0]
        val dist = it.slice(1 until it.length).toInt()
        for (i in 0 until dist) {
            if (dir == 'U') {
                pos[1] += 1
            } else if (dir == 'D') {
                pos[1] -= 1
            } else if (dir == 'R') {
                pos[0] += 1
            } else {
                pos[0] -= 1
            }
            if (grid[pos[0]][pos[1]] == 1) {
                intersects.add(Pair(pos[0], pos[1]))
            }
            wire2Locations.add(Pair(pos[0], pos[1]))
            grid[pos[0]][pos[1]] = 2
        }
    }
    println("Intersects: $intersects")
    val intersectLoc = intersects.minBy { abs(it.first - grid.size / 2) + abs(it.second - grid.size / 2) }!!
    println("Wire 1 length: ${wire1Locations.size} Wire 2 length: ${wire2Locations.size}")
    val wireIntersectLoc = intersects.minBy { loc ->
        wire1Locations.indexOfFirst { it == loc } + wire2Locations.indexOfFirst { it == loc }
    }!!
    println(wire1Locations.indexOfFirst { it == wireIntersectLoc } + wire2Locations.indexOfFirst { it == wireIntersectLoc })
}
