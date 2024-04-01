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
            return getTileContent(col, row) == Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun isTowerTile(col: Int, row: Int) : Boolean {
        if (isInitialised) {
            return getTileContent(col, row) < Tiles.EMPTY.value
        } else {
            return false
        }
    }

    fun getTileContent(col: Int, row: Int) : Int {
        return mapLink.getTileContent(col, row)
    }

    fun getWaveEnemies(n: Int): List<Enemy>{
        var l : List<Enemy> = emptyList()

        if (isInitialised) {
            l =  mapLink.getWave(n).getEnemies().toList()
        }
        return l
    }

    fun getPathEncoding() : List<Int> {
        return mapLink.getPathEncoding()
    }
}