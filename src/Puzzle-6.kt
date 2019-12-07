import java.io.File

fun main() {
    val input = File("src/inputs/6.in").readLines()
    val orbits = mutableMapOf("COM" to Body(null, "COM"))
    input.forEach {
        val orbitData = it.split(")")
        if (orbits[orbitData[0]] == null) {
            orbits[orbitData[0]] = Body(null, orbitData[0])
        }
        if (orbits[orbitData[1]] == null) {
            orbits[orbitData[1]] = Body(orbits[orbitData[0]], orbitData[1])
        } else {
            orbits[orbitData[1]]!!.orbiting = orbits[orbitData[0]]
        }
    }
    println("Number of orbits: ${orbits.keys.sumBy { orbits[it]!!.getOrbits() }}")
    val youPath = orbits["YOU"]!!.orbiting!!.getOrbitPath()
    val sanPath = orbits["SAN"]!!.orbiting!!.getOrbitPath()
    youPath.reverse()
    sanPath.reverse()
    var name = "COM"
    var index = 0
    while (youPath.size > index + 1 && sanPath.size > index + 1 && youPath[index] == sanPath[index]) {
        index += 1
    }
    name = youPath[index - 1]
    println("Orbital transfers: ${orbits["YOU"]!!.orbiting!!.getNumberOfOrbitsUntil(name) + orbits["SAN"]!!.orbiting!!.getNumberOfOrbitsUntil(name)}")
}

class Body(var orbiting: Body?, val name: String) {
    fun getOrbits(): Int {
        var parent: Body? = this
        var orbits = 0
        while (parent!!.orbiting != null) {
            parent = parent.orbiting
            orbits += 1
        }
        return orbits
    }

    fun getOrbitPath(): MutableList<String> {
        var parent: Body? = this
        var orbiters = mutableListOf<String>()
        while (parent!!.orbiting != null) {
            parent = parent.orbiting
            orbiters.add(parent!!.name)
        }
        return orbiters
    }

    fun getNumberOfOrbitsUntil(name: String): Int {
        assert(name in this.getOrbitPath())
        var parent: Body? = this
        var orbits = 0
        while (parent!!.name != name) {
            parent = parent.orbiting
            orbits += 1
        }
        return orbits
    }

}