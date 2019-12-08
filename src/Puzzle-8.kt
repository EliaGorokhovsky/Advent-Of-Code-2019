import java.io.File

fun main() {
    val input = File("src/inputs/8.in").readLines()[0].toCharArray().map { it.toString().toInt() }.chunked(25 * 6)
    val layer = input.minBy { it.count {num -> num == 0} }!!
    println(layer.count { num -> num == 1 } * layer.count{ num -> num == 2 })
    val compositeLayer = mutableListOf<Int>()
    (0 until 25 * 6).forEach {
        var i = 0
        while (input[i][it] == 2) {
            i++
        }
        compositeLayer.add(input[i][it])
    }
    compositeLayer.chunked(25).map { it.joinToString() }.forEach { println(it) }
}
