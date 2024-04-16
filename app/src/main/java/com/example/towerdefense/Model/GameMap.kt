package com.example.towerdefense.model


class GameMap(val nWave:Int = 12) {
    companion object {
        const val N_ROWS: Int = 8
        const val N_COLUMNS: Int = 16

        const val PX_PER_TILE = 120

        fun gridToPixel(col: Int, row: Int): Pair<Int, Int>{
            val px = (col * PX_PER_TILE)
            val py = (row * PX_PER_TILE)
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

    private val mFirstTile : Pair<Int, Int> = Pair(3,0)

    private val mGrid  = MutableList(N_COLUMNS) { MutableList(N_ROWS) { Tiles.EMPTY.value } }

    private val mWaves: MutableList<Wave> = mutableListOf()

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
    fun getWave(n:Int): Wave { return this.mWaves[n] }

    fun getPathEncoding() : List<Int> { return _pathEncoding }

    fun getTileContent(col: Int, row: Int) : Int { return mGrid[col][row] }

    fun getFirstTile() : Pair<Int, Int> { return mFirstTile }

    fun setTileContent(col: Int, row: Int, value: Int) {
        if (isValidCol(col) && isValidRow(row) && value <= Tiles.EMPTY.value) {
            mGrid[col][row] = value
        }
    }

    //************************************* Private methods ************************************* //
    private fun initWaves() {
        for (i in 0 until nWave)
            mWaves.add(Wave(i))
    }

    private fun initGrid(){
        var col = mFirstTile.first
        var row = mFirstTile.second

        mGrid[col][row] = 1

        for (i in _pathEncoding.indices) {
            when (_pathEncoding[i]){
                0 -> mGrid[++col][row] = i + 2
                1 -> mGrid[col][--row] = i + 2
                2 -> mGrid[--col][row] = i + 2
                3 -> mGrid[col][++row] = i + 2
            }
        }
    }
}