package com.example.towerdefense.model

import kotlin.math.roundToInt

class MapGame {
    val N_ROWS: Int = 8
    val N_COLUMNS: Int = 16

    val EMPTY_TILE = 0
    val TOWER_TILE = -1

    val PX_PER_TILE = 120

    // Encoding of the tile which contains a road (grid x, grid y)
    private val FIRST_TILE : Pair<Int, Int> = Pair(3,0)

    // Absolut path: 0 = right, 1 = up, 2 = left, 3 = down
    private val PATH_ENCODING = listOf(
        3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3
    )

    val mapGrid  = MutableList(N_COLUMNS) { MutableList(N_ROWS) { EMPTY_TILE } }

    init {
        var col = FIRST_TILE.first
        var row = FIRST_TILE.second

        mapGrid[col][row] = 1

        for (i in PATH_ENCODING.indices) {
            when (PATH_ENCODING[i]){
                0 -> mapGrid[++col][row] = i + 2
                1 -> mapGrid[col][--row] = i + 2
                2 -> mapGrid[--col][row] = i + 2
                3 -> mapGrid[col][++row] = i + 2
            }
        }
    }

    fun gridToPixel(gridX: Int, gridY: Int): Pair<Int, Int>{
        val px = (gridX * PX_PER_TILE + 0.5 * PX_PER_TILE).roundToInt()
        val py = (gridY * PX_PER_TILE + 0.5 * PX_PER_TILE).roundToInt()
        return Pair(px, py)
    }

    fun pixelToGrid(px : Int, py: Int): Pair<Int, Int>{
        val gridX = (px.floorDiv(PX_PER_TILE))
        val gridY = (py.floorDiv(PX_PER_TILE))
        return Pair(gridX,gridY)
    }

    fun isEmptyTile(gridX: Int, gridY: Int) : Boolean {
        return mapGrid[gridX][gridY] == EMPTY_TILE
    }

    fun isTowerTile(gridX: Int, gridY: Int) : Boolean {
        return mapGrid[gridX][gridY] == TOWER_TILE
    }
}