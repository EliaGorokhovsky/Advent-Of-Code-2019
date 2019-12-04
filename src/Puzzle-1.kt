import java.io.File

fun getFuelCost(moduleCost: Int): Int {
    var currentFuel = moduleCost
    var baseFuel = moduleCost
    while (baseFuel >= 6) {
        baseFuel = baseFuel / 3 - 2
        currentFuel += baseFuel
    }
    return currentFuel
}

fun main() {
    println(File("src/inputs/1.in").readLines().map { getFuelCost(it.toInt() / 3 - 2) }.sum())
}