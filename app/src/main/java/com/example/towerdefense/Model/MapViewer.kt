package com.example.towerdefense.model

object MapViewer {
    //*************************************** Variables ***************************************** //
    private lateinit var mMapLink : GameMap
    private var mIsInitialised : Boolean = false

    //*************************************** Constructor *************************************** //


    //************************************** Map Setter ***************************************** //
    fun linkMap(m : GameMap) {
        if (!mIsInitialised) {
            mMapLink = m
            mIsInitialised = true
        }
    }

    //************************************* Map accessors  ************************************** //
    fun isEmptyTile(col: Int, row: Int) : Boolean {
        if (mIsInitialised) {
            return getTileContent(col, row) == Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun isTowerTile(col: Int, row: Int) : Boolean {
        if (mIsInitialised) {
            return getTileContent(col, row) < Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun isRoad(col: Int, row: Int) : Boolean {
        if (mIsInitialised) {
            return getTileContent(col, row) > Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun getTileContent(col: Int, row: Int) : Int {
        return mMapLink.getTileContent(col, row)
    }

    fun getWaveEnemies(n: Int): List<Enemy>{
        var l : List<Enemy> = emptyList()

        if (mIsInitialised) {
            l =  mMapLink.getWave(n).getEnemies().toList()
        }
        return l
    }

    fun getPathEncoding() : List<Int> {
        return mMapLink.getPathEncoding()
    }

    fun getFirstTile() : Pair<Int, Int> {
        return mMapLink.getFirstTile()
    }
}