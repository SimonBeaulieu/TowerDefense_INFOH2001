package com.example.towerdefense.model

import kotlin.math.roundToInt

class GameMap(val nWave:Int = 12) {
    companion object {
        const val N_ROWS: Int = 8
        const val N_COLUMNS: Int = 16

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

        fun isValidRow(row: Int) : Boolean {
            return row in 0..<N_ROWS
        }

        fun isValidCol(col: Int) : Boolean {
            return col in 0..<N_COLUMNS
        }
    }

    private val _firstTile : Pair<Int, Int> = Pair(3,0)

    private val _grid  = MutableList(N_COLUMNS) { MutableList(N_ROWS) { Tiles.EMPTY.value } }

    private val _waves: MutableList<Wave> = mutableListOf()

    // Absolut path: 0 = right, 1 = up, 2 = left, 3 = down.
    // !!!SB: Rendre disponible dans constructeur
    private val _pathEncoding = listOf(
        3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3
    )

    //*************************************** Constructor *************************************** //
    init {
        initGrid()
        initWaves()
    }

    //************************************* Public methods ************************************** //
    fun getWave(n:Int): Wave { return this._waves[n] }

    fun getPathEncoding() : List<Int> { return _pathEncoding; }

    fun getTileContent(col: Int, row: Int) : Int { return _grid[col][row]; }

    fun setTileContent(col: Int, row: Int, value: Int) {
        if (isValidCol(col) && isValidRow(row) && value <= Tiles.EMPTY.value) {
            _grid[col][row] = value
        }
    }

    //************************************* Private methods ************************************* //
    private fun initWaves() {
        for (i in 0 until nWave)
            _waves.add(Wave(i))
    }

    private fun initGrid(){
        var col = _firstTile.first
        var row = _firstTile.second

        _grid[col][row] = 1

        for (i in _pathEncoding.indices) {
            when (_pathEncoding[i]){
                0 -> _grid[++col][row] = i + 2
                1 -> _grid[col][--row] = i + 2
                2 -> _grid[--col][row] = i + 2
                3 -> _grid[col][++row] = i + 2
            }
        }
    }
}