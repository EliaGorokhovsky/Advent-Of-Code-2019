import java.io.File

fun main () {
    val input = File("src/inputs/7.in").readLines()[0].split(",").map { it.toInt() }.toMutableList()
    println(getPermutations(mutableListOf(5, 6, 7, 8, 9)).map { runAmplifiers(it, input) }.max())
}

fun getPermutations(list: MutableList<Int>): MutableList<MutableList<Int>> {
    if (list.size == 1) {
        return mutableListOf(list)
    }
    val permutations = mutableListOf<MutableList<Int>>()
    list.forEach { end ->
        val subPermutations = getPermutations(list.minus(end).toMutableList())
        subPermutations.forEach {
            it.add(end)
            permutations.add(it)
        }
    }
    return permutations
}

fun runAmplifiers(phaseSettings: MutableList<Int>, code: MutableList<Int>): Int {
    var outputs = Array(5) { Pair(0, 0) }
    var nextInputs = Array(5) { mutableListOf(phaseSettings[it]) }
    nextInputs[0].add(0)
    val codes = Array(5) { code.toMutableList() }
    val pointers = Array(5) { 0 }
    var index = 0
    while (pointers[index] != -1) {
        println("Running amplifier $index from pointer ${pointers[index]}")
        val out = runIntcode3(codes[index], nextInputs[index], pointers[index])
        nextInputs[index].clear()
        codes[index] = out.first
        pointers[index] = out.second.first
        if (out.second.second != -1)
            outputs[index] = out.second
        index = (index + 1) % 5
        nextInputs[index].add(out.second.second)
    }
    return outputs.last().second
}

fun runIntcode3(code: MutableList<Int>, inputs: MutableList<Int>, instructionPointer: Int): Pair<MutableList<Int>, Pair<Int, Int>> {
    var pointer = instructionPointer
    var inputIndex = 0
    var output = -1
    while (pointer < code.size) {
        val cmd = code[pointer].toString().padStart(5, '0')
        val opcode = cmd.slice(3..4).toInt()
        val params = cmd.slice(0..2).map { it.toString().toInt() }
        println("Pointer $pointer / ${code.size}: $opcode")
        if (opcode == 99) {
            break
        } else if (opcode == 1) {
            code[code[pointer + 3]] = code[if (params[2] == 0) code[pointer + 1] else pointer + 1] + code[if (params[1] == 0) code[pointer + 2] else pointer + 2]
            pointer += 4
        } else if (opcode == 2) {
            code[code[pointer + 3]] = code[if (params[2] == 0) code[pointer + 1] else pointer + 1] * code[if (params[1] == 0) code[pointer + 2] else pointer + 2]
            pointer += 4
        } else if (opcode == 3) {
            println("Taking input: ${inputs[inputIndex]} ")
            code[code[pointer + 1]] = inputs[inputIndex++]
            pointer += 2
        } else if (opcode == 4) {
            println("Out at pointer $pointer: ${code[if (params[2] == 0) code[pointer + 1] else pointer + 1]}")
            output = code[if (params[2] == 0) code[pointer + 1] else pointer + 1]
            pointer += 2
            return Pair(code, Pair(pointer, code[if (params[2] == 0) code[pointer - 1] else pointer - 1]))
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
    println("Terminating on $output")
    return Pair(code, Pair(-1, output))
}