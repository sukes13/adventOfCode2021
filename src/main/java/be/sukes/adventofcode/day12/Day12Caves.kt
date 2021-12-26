package be.sukes.adventofcode.day12


class CavesRecord(cavesStrings: List<String>) {
    val caves: Set<Cave> = cavesStrings.toCaves()
    var paths: MutableList<String> = mutableListOf()

    fun solution() = paths.filter { it.contains("end") }.size

    fun search(): CavesRecord {
        doSearch { true }.also { return this }
    }

    fun searchV2(): CavesRecord {
        doSearch { it.hasDubbler() }.also { return this }
    }

    private fun doSearch(extraCheck: (Trail) -> Boolean) {
        searchPaths(caves.byName("start"), extraCheck)
        paths = paths.filter { it.contains("end") }.toMutableList()
    }

    private fun searchPaths(cave: Cave, extraCheck: (Trail) -> Boolean, trail: Trail = Trail()) {
        val updatedTrail = cave.name.addToPath(trail)
        val validSteps = validSteps(cave, updatedTrail, extraCheck)
        when {
            validSteps.isEmpty() -> paths.add(updatedTrail.toString())
            else -> validSteps.forEach {
                searchPaths(it, extraCheck, updatedTrail)
            }
        }
    }

    private fun String.addToPath(trail: Trail) = trail.copy(path = trail.path + this)

    private fun validSteps(cave: Cave, trail: Trail, extraCheck: (Trail) -> Boolean) =
            when (cave.name) {
                "end" -> emptyList()
                else -> cave.neighbours.map { caves.byName(it) }
                                        .filterNot {
                                            it.name == "start" ||
                                            extraCheck(trail) && it.name.first().isLowerCase() && it.name in trail.path
                                        }
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

data class Trail(var path: List<String> = mutableListOf()) {
    fun hasDubbler(): Boolean = path.filterNot { it == "start" || it == "end" }
            .filter { it.first().isLowerCase() }
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .isNotEmpty()

    override fun toString() = path.joinToString(",")
}

data class Cave(val name: String) {
    val neighbours = mutableSetOf<String>()

    fun addNeighbour(cave2: String) {
        neighbours.add(cave2)
    }

    override fun toString(): String {
        return "name='$name', neighbours=$neighbours"
    }
}