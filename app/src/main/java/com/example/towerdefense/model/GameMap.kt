package com.example.towerdefense.model

import com.example.towerdefense.model.service.GameMapReadService
import com.example.towerdefense.model.service.ServiceLocator

class GameMap(val nWave:Int = 12) {
    private val mFirstTile : Pair<Int, Int> = Pair(3,0)

    private val mGrid  = MutableList(GameMapUtils.N_COLUMNS) { MutableList(GameMapUtils.N_ROWS) { Tiles.EMPTY.value } }

    private val mWaves: MutableList<Wave> = mutableListOf()

    // Absolut path: 0 = right, 1 = up, 2 = left, 3 = down.
    private val mPathEncoding = listOf(
        3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3
    )

    //*************************************** Constructor *************************************** //
    init {
        initGrid()
        initWaves()
        ServiceLocator.addService(GameMapReadService(this))
    }

    //************************************* Public methods ************************************** //
    fun isEmptyTile(col: Int, row: Int): Boolean {
        return getTileContent(col, row) == Tiles.EMPTY.value
    }

    fun isTowerTile(col: Int, row: Int): Boolean {
        return getTileContent(col, row) < Tiles.EMPTY.value
    }

    fun isRoadTile(col: Int, row: Int): Boolean{
        return getTileContent(col, row) > Tiles.EMPTY.value
    }

    fun getTileContent(col: Int, row: Int) : Int {
        if (GameMapUtils.isValidCol(col) && GameMapUtils.isValidRow(row)) {
            return mGrid[col][row]
        } else {
            return Tiles.INVALID.value
        }
    }

    fun getPathEncoding() : List<Int> { return mPathEncoding.toList() }

    fun getFirstTile() : Pair<Int, Int> { return mFirstTile.copy() }

    fun getWave(n:Int): Wave { return this.mWaves[n] }

    fun setTileContent(col: Int, row: Int, value: Int) {
        if (GameMapUtils.isValidCol(col) && GameMapUtils.isValidRow(row) && value <= Tiles.EMPTY.value) {
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

        for (i in mPathEncoding.indices) {
            when (mPathEncoding[i]) {
                0 -> mGrid[++col][row] = i + 2
                1 -> mGrid[col][--row] = i + 2
                2 -> mGrid[--col][row] = i + 2
                3 -> mGrid[col][++row] = i + 2
            }
        }
    }
}
