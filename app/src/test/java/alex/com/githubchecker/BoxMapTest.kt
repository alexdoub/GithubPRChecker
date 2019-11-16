package alex.com.githubchecker

import org.junit.Test

class MapTest {

    companion object {
        val hardBoxMap = mutableListOf<String>().apply {
            add("###########")
            add("#G##......#")
            add("#.#.#..####")
            add("#....B....#")
            add("#.####....#")
            add("#.......S.#")
            add("###########")
        }
        val simpleBoxMap = mutableListOf<String>().apply {
            add("#G..B...S#")
        }


        val simplePath = mutableListOf<String>().apply {
            add("G#######S")
            add(".........")
        }
        val zigZag = mutableListOf<String>().apply {
            add("G#...#...")
            add(".#.#.#.#.")
            add("...#...#S")
        }
        var mapToUse = simpleBoxMap


        val START = "S"
        val GOAL = "G"
        val BOX = "#"

        val WRONG_STEP = 50000
    }

    @Test
    fun testMyStuff() {
        var startPosition = Pair(-1, -1)
        var goalPosition = Pair(-1, -1)
        for ((yIndex, lines) in mapToUse.withIndex()) {
            for ((xIndex, char) in lines.withIndex()) {
                if (char.toString() == GOAL) {
                    goalPosition = Pair(xIndex, yIndex)
                }
                if (char.toString() == START) {
                    startPosition = Pair(xIndex, yIndex)
                }
            }
        }

        val minPushes = getMinimumPushes(startPosition, goalPosition)
        println("MinPushes = ${minPushes}")
    }



    //////////

    fun getMinimumPushes(startPosition: Pair<Int, Int>, endPosition: Pair<Int, Int>): Int {

        val minMoves =

                return 0
    }


    fun getMinimumMoves(startPosition: Pair<Int, Int>, endPosition: Pair<Int, Int>, blockedLocations: ArrayList<Pair<Int, Int>>): Int {

        val newBlockedLocations: ArrayList<Pair<Int, Int>> = ArrayList<Pair<Int, Int>>(blockedLocations)
        newBlockedLocations.add(startPosition)

        if (startPosition == endPosition) {
            return 0
        }

        val topPosition = startPosition.getTop()
        val topPathMoves =
                if (topPosition.isValid(newBlockedLocations)) {
                    1 + getMinimumMoves(topPosition, endPosition, newBlockedLocations)
                } else {
                    WRONG_STEP
                }

        val botPosition = startPosition.getBottom()
        val botPathMoves =
                if (botPosition.isValid(newBlockedLocations)) {
                    1 + getMinimumMoves(botPosition, endPosition, newBlockedLocations)
                } else {
                    WRONG_STEP
                }

        val leftPosition = startPosition.getLeft()
        val leftPathMoves =
                if (leftPosition.isValid(newBlockedLocations)) {
                    1 + getMinimumMoves(leftPosition, endPosition, newBlockedLocations)
                } else {
                    WRONG_STEP
                }

        val rightPosition = startPosition.getRight()
        val rightPathMoves =
                if (rightPosition.isValid(newBlockedLocations)) {
                    1 + getMinimumMoves(rightPosition, endPosition, newBlockedLocations)
                } else {
                    WRONG_STEP
                }

        var shortestMoves = botPathMoves
        if (topPathMoves < shortestMoves) shortestMoves = topPathMoves
        if (leftPathMoves < shortestMoves) shortestMoves = leftPathMoves
        if (rightPathMoves < shortestMoves) shortestMoves = rightPathMoves
        return shortestMoves
    }

    fun Pair<Int, Int>.isValid(visitedOrBox: ArrayList<Pair<Int, Int>>): Boolean {
        val row = second
        val column = first

        if (row < 0 || row > mapToUse.size - 1)
            return false
        if (column < 0 || column > mapToUse[0].length - 1)
            return false
        if (mapToUse[row][column].toString() == BOX)
            return false
        if (visitedOrBox.contains(this))
            return false
        return true
    }

    fun Pair<Int, Int>.getLeft() = Pair(first - 1, second)
    fun Pair<Int, Int>.getRight() = Pair(first + 1, second)
    fun Pair<Int, Int>.getTop() = Pair(first, second - 1)
    fun Pair<Int, Int>.getBottom() = Pair(first, second + 1)
}
// FIRST is the row. Starting at top = 0
// SECOND is the column. Starting at top = 0