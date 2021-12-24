package be.sukes.adventofcode.day12


class CavesRecord(cavesStrings: List<String>) {
    val caves: Set<Cave> = cavesStrings.toCaves()
    var paths: MutableList<String> = mutableListOf()

    fun solutionOne() = paths.filter { it.contains("end") }.size

    fun search() {
        searchPaths(caves.byName("start"))
        paths = paths.filter { it.contains("end") }.toMutableList()
    }

    private fun searchPaths(cave: Cave, path: List<String> = mutableListOf()) {
        val newPath = cave.addToPath(path)
        val validSteps = validSteps(cave,path)
        when {
            validSteps.isEmpty() -> {
                paths.add(newPath.joinToString (","))
            }
            else -> {
                validSteps.forEach {
                    when (it.name) {
                        "end" -> paths.add(it.addToPath(newPath).joinToString (","))
                        else -> searchPaths(it,newPath)
                    }
                }
            }
        }

    }

    private fun Cave.addToPath(path: List<String>) =
            path + this.name

    private fun validSteps(cave: Cave, path: List<String>): List<Cave> {
        return cave.neighbours.map { caves.byName(it) }
                .filterNot { it.name.first().isLowerCase() && it.name in path }
    }

    private fun List<String>.toCaves() =
            this.fold(mutableSetOf<Cave>()) { caves, line ->
                caves.plus(
                        line.trim()
                                .split("-").windowed(2)
                                .map { (caveString1, caveString2) ->
                                    val cave = caves.byName(caveString1)
                                    val cave2 = caves.byName(caveString2)
                                    addCaveCoupling(cave, cave2)
                                }.flatten().toSet()
                ).toMutableSet()
            }.toSet()

    private fun addCaveCoupling(cave: Cave, cave2: Cave): List<Cave> {
        cave.addNeighbour(cave2.name)
        cave2.addNeighbour(cave.name)
        return listOf(cave, cave2)
    }

    private fun Set<Cave>.byName(caveString: String): Cave {
        return this.singleOrNull { it.name == caveString } ?: Cave(caveString)
    }
}

data class Cave(val name: String) {
    val neighbours = mutableSetOf<String>()
    private var visited = false

    fun addNeighbour(cave2: String) {
        neighbours.add(cave2)
    }

    override fun toString(): String {
        return "name='$name', neighbours=$neighbours"
    }
}