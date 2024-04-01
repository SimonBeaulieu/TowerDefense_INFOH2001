package com.example.towerdefense.model

import kotlin.math.roundToInt

class GameMap(val nWave:Int = 12) {
    companion object {
        const val N_ROWS: Int = 8
        const val N_COLUMNS: Int = 16

        const val EMPTY_TILE = 0
        const val TOWER_TILE = -1
        const val PX_PER_TILE = 120

        fun gridToPixel(col: Int, row: Int): Pair<Int, Int>{
            val px = (col * PX_PER_TILE + 0.5 * PX_PER_TILE).roundToInt()
            val py = (row * PX_PER_TILE + 0.5 * PX_PER_TILE).roundToInt()
            return Pair(px, py)

        }

        fun pixelToGrid(px : Int, py: Int): Pair<Int, Int>{
            val col = (px.floorDiv(PX_PER_TILE))
            val row = (py.floorDiv(PX_PER_TILE))
            return Pair(col,row)
        }
    }

    private val firstTile : Pair<Int, Int> = Pair(3,0)

    private val grid  = MutableList(N_COLUMNS) { MutableList(N_ROWS) { EMPTY_TILE } }

    private val waves: MutableList<Wave> = mutableListOf()

    // Absolut path: 0 = right, 1 = up, 2 = left, 3 = down.
    // !!!SB: Rendre disponible dans constructeur
    private val pathEncoding = listOf(
        3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3
    )

    //*************************************** Constructor *************************************** //
    init {
        initGrid()
        initWaves()
    }

    //************************************* Public methods ************************************** //
    fun getWave(n:Int): Wave { return this.waves[n] }

    fun getPathEncoding() : List<Int> { return pathEncoding; }

    fun getTileContent(col: Int, row: Int) : Int { return grid[col][row]; }

    fun setTileContent(col: Int, row: Int, value: Int) {
        if (isValidCol(col) && isValidRow(row) && value <= EMPTY_TILE) {
            grid[col][row] = value
        }
    }

    //************************************* Private methods ************************************* //
    private fun initWaves() {
        for (i in 0 until nWave)
            waves.add(Wave(i))
    }

    private fun initGrid(){
        var col = firstTile.first
        var row = firstTile.second

        grid[col][row] = 1

        for (i in pathEncoding.indices) {
            when (pathEncoding[i]){
                0 -> grid[++col][row]    = i + 2
                1 -> grid[col][--row] = i + 2
                2 -> grid[--col][row] = i + 2
                3 -> grid[col][++row] = i + 2
            }
        }
    }

    private fun isValidRow(row: Int) : Boolean {
        return row in 0..<N_ROWS
    }

    private fun isValidCol(col: Int) : Boolean {
        return col in 0..<N_COLUMNS
    }
}