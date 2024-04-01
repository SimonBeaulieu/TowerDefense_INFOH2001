package com.example.towerdefense.model

object MapViewer {
    //*************************************** Variables ***************************************** //
    private lateinit var mapLink : GameMap
    private var isInitialised : Boolean = false

    //*************************************** Constructor *************************************** //


    //************************************** Map Setter ***************************************** //
    fun linkMap(m : GameMap) {
        if (!isInitialised) {
            mapLink = m
            isInitialised = true
        }
    }

    //************************************* Map accessors  ************************************** //
    fun isEmptyTile(col: Int, row: Int) : Boolean {
        if (isInitialised) {
            return getTileContent(col, row) == GameMap.EMPTY_TILE
        } else {
            return false
        }
    }

    fun isTowerTile(col: Int, row: Int) : Boolean {
        if (isInitialised) {
            return getTileContent(col, row) == GameMap.TOWER_TILE
        } else {
            return false
        }
    }

    fun getTileContent(col: Int, row: Int) : Int {
        return mapLink.getTileContent(col, row)
    }

    fun getWaveEnemies(nWave: Int): MutableList<Enemy>{
        var l = mutableListOf<Enemy>()

        if (isInitialised) {
            l =  mapLink.getWaves()[nWave].getWaveEnemies()
        }

        return l
    }
}