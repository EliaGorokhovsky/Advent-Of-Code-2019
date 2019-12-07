fun main() {
    val inputMin = 146810
    val inputMax = 612564
    println((inputMin..inputMax).count { checkPassword(it) })
}

fun checkPassword(pass: Int): Boolean {
    var prevChar = -1
    var repeated = 1
    var properlyRepeated = false // No more than two in a row
    pass.toString().toCharArray().map { it.toInt() }.forEach {
        if (it < prevChar) return false
        if (it != prevChar) {
            if (repeated == 2) {
                properlyRepeated = true
            }
            repeated = 1
        }
        if (it == prevChar) repeated++
        prevChar = it
    }
    return properlyRepeated || pass.toString().toCharArray()[5] == pass.toString().toCharArray()[4] && pass.toString().toCharArray()[4] != pass.toString().toCharArray()[3]
}