import java.io.File

fun main () {
    var input = File("src/inputs/2.in").readLines()[0].split(",").map { it.toInt() }.toMutableList()
    while (runIntcode(input.toMutableList()) != 19690720) {
        input[1] += 1
        if (input[1] == 100) {
            input[1] = 0
            input[2] += 1
        }
    }
    println(100 * input[1] + input[2])
}

fun runIntcode(code: MutableList<Int>): Int {
    var pointer = 0
    while (pointer < code.size - 3) {
        if (code[pointer] == 99) {
            break
        } else if (code[pointer] == 1) {
            code[code[pointer + 3]] = code[code[pointer + 1]] + code[code[pointer + 2]]
        } else if (code[pointer] == 2) {
            code[code[pointer + 3]] = code[code[pointer + 1]] * code[code[pointer + 2]]
        }
        pointer += 4
    }
    return code[0]
}