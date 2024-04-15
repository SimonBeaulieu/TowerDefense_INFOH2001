package com.example.towerdefense.model

object MapViewer {
    //*************************************** Variables ***************************************** //
    private lateinit var _mapLink : GameMap
    private var _isInitialised : Boolean = false

    //*************************************** Constructor *************************************** //


    //************************************** Map Setter ***************************************** //
    fun linkMap(m : GameMap) {
        if (!_isInitialised) {
            _mapLink = m
            _isInitialised = true
        }
    }

    //************************************* Map accessors  ************************************** //
    fun isEmptyTile(col: Int, row: Int) : Boolean {
        if (_isInitialised) {
            return getTileContent(col, row) == Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun isTowerTile(col: Int, row: Int) : Boolean {
        if (_isInitialised) {
            return getTileContent(col, row) < Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun isRoad(col: Int, row: Int) : Boolean {
        if (_isInitialised) {
            return getTileContent(col, row) > Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun getTileContent(col: Int, row: Int) : Int {
        return _mapLink.getTileContent(col, row)
    }

    fun getWaveEnemies(n: Int): List<Enemy>{
        var l : List<Enemy> = emptyList()

        if (_isInitialised) {
            l =  _mapLink.getWave(n).getEnemies().toList()
        }
        return l
    }

    fun getPathEncoding() : List<Int> {
        return _mapLink.getPathEncoding()
    }
}