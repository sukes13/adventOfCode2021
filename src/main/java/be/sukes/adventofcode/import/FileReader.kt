package be.sukes.adventofcode.import

class FileReader {

    fun readLines(fileName : String) : List<String> =
            this::class.java.getResourceAsStream(fileName).bufferedReader().readLines()


}