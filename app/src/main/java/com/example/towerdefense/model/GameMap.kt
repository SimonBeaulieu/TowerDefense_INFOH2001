package com.example.towerdefense.model

import kotlin.math.roundToInt

class GameMap {
    //**************************************** Variables **************************************** //
    val nRows: Int = 8
    val nColumns: Int = 16

    val emptyTile = 0
    val towerTile = -1

    val pxPerTile = 120

    // Encoding of the tile which contains a road (grid x, grid y)
    private val firstTile : Pair<Int, Int> = Pair(3,0)

    // Absolut path: 0 = right, 1 = up, 2 = left, 3 = down
    private val pathEncoding = listOf(
        3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3
    )

    val mapGrid  = MutableList(nColumns) { MutableList(nRows) { emptyTile } }

    //*************************************** Constructor *************************************** //
    init {
        var col = firstTile.first
        var row = firstTile.second

        mapGrid[col][row] = 1

        for (i in pathEncoding.indices) {
            when (pathEncoding[i]){
                0 -> mapGrid[++col][row] = i + 2
                1 -> mapGrid[col][--row] = i + 2
                2 -> mapGrid[--col][row] = i + 2
                3 -> mapGrid[col][++row] = i + 2
            }
        }
    }


    //************************************* Public methods ************************************** //
    fun gridToPixel(gridX: Int, gridY: Int): Pair<Int, Int>{
        val px = (gridX * pxPerTile + 0.5 * pxPerTile).roundToInt()
        val py = (gridY * pxPerTile + 0.5 * pxPerTile).roundToInt()
        return Pair(px, py)
    }

    fun pixelToGrid(px : Int, py: Int): Pair<Int, Int>{
        val gridX = (px.floorDiv(pxPerTile))
        val gridY = (py.floorDiv(pxPerTile))
        return Pair(gridX,gridY)
    }

    fun isEmptyTile(gridX: Int, gridY: Int) : Boolean {
        return mapGrid[gridX][gridY] == emptyTile
    }

    fun isTowerTile(gridX: Int, gridY: Int) : Boolean {
        return mapGrid[gridX][gridY] == towerTile
    }

    //************************************* Private methods ************************************* //

}