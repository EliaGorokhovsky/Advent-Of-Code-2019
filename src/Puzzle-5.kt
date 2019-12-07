import java.io.File

fun main () {
    var input = File("src/inputs/5.in").readLines()[0].split(",").map { it.toInt() }.toMutableList()
    runIntcode2(input)
}

fun runIntcode2(code: MutableList<Int>): Int {
    var pointer = 0
    while (pointer < code.size) {
        val cmd = code[pointer].toString().padStart(5, '0')
        val opcode = cmd.slice(3..4).toInt()
        val params = cmd.slice(0..2).map { it.toString().toInt() }
        if (opcode == 99) {
            break
        } else if (opcode == 1) {
            code[code[pointer + 3]] = code[if (params[2] == 0) code[pointer + 1] else pointer + 1] + code[if (params[1] == 0) code[pointer + 2] else pointer + 2]
            pointer += 4
        } else if (opcode == 2) {
            code[code[pointer + 3]] = code[if (params[2] == 0) code[pointer + 1] else pointer + 1] * code[if (params[1] == 0) code[pointer + 2] else pointer + 2]
            pointer += 4
        } else if (opcode == 3) {
            println("Taking input: ")
            code[code[pointer + 1]] = readLine()!!.toInt()
            pointer += 2
        } else if (opcode == 4) {
            println("Out: ${code[if (params[2] == 0) code[pointer + 1] else pointer + 1]}")
            pointer += 2
        }
        else if (opcode == 5) {
            if (code[if (params[2] == 0) code[pointer + 1] else pointer + 1] != 0) {
                pointer = code[if (params[1] == 0) code[pointer + 2] else pointer + 2]
            } else {
                pointer += 3
            }
        }
        else if (opcode == 6) {
            if (code[if (params[2] == 0) code[pointer + 1] else pointer + 1] == 0) {
                pointer = code[if (params[1] == 0) code[pointer + 2] else pointer + 2]
            } else {
                pointer += 3
            }
        }
        else if (opcode == 7) {
            if (code[if (params[2] == 0) code[pointer + 1] else pointer + 1] < code[if (params[1] == 0) code[pointer + 2] else pointer + 2]) {
                code[if (params[0] == 0) code[pointer + 3] else pointer + 3] = 1
            } else {
                code[if (params[0] == 0) code[pointer + 3] else pointer + 3] = 0
            }
            pointer += 4
        }
        else if (opcode == 8) {
            if (code[if (params[2] == 0) code[pointer + 1] else pointer + 1] == code[if (params[1] == 0) code[pointer + 2] else pointer + 2]) {
                code[if (params[0] == 0) code[pointer + 3] else pointer + 3] = 1
            } else {
                code[if (params[0] == 0) code[pointer + 3] else pointer + 3] = 0
            }
            pointer += 4
        }
    }
    return code[0]
}