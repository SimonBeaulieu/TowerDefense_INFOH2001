package com.example.towerdefense.model

import kotlin.math.roundToInt

object GameMap {

    //*************************************** Variables ***************************************** //
    private var map : Map = Map()

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************** //
    fun gridToPixel(gridX: Int, gridY: Int): Pair<Int, Int>{
        val px = (gridX * map.pxPerTile + 0.5 * map.pxPerTile).roundToInt()
        val py = (gridY * map.pxPerTile + 0.5 * map.pxPerTile).roundToInt()
        return Pair(px, py)

    }

    fun pixelToGrid(px : Int, py: Int): Pair<Int, Int>{
        val gridX = (px.floorDiv(map.pxPerTile))
        val gridY = (py.floorDiv(map.pxPerTile))
        return Pair(gridX,gridY)
    }

    fun isEmptyTile(gridX: Int, gridY: Int) : Boolean {
        return map.mapGrid[gridX][gridY] == map.emptyTile
    }

    fun isTowerTile(gridX: Int, gridY: Int) : Boolean {
        return map.mapGrid[gridX][gridY] == map.towerTile
    }

    fun loadMap(mapToLoad:Map){
        map = mapToLoad
    }

    fun getMap() : Map { return this.map }

    fun getWaveEnemies(nWave: Int): MutableList<Enemy>{
        return map.getWaves()[nWave].getWaveEnemies()
    }

    //************************************* Private methods ************************************* //

}