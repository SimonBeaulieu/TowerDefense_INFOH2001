package com.example.towerdefense.model.service

import com.example.towerdefense.model.GameMap

class GameMapReadService(private val gameMap : GameMap) : Service {
    //************************************* Read methods ************************************** //
    override fun getServiceClass() : Class<*> {
        return GameMapReadService::class.java
    }

    fun isEmptyTile(col : Int, row : Int) : Boolean {
        return gameMap.isEmptyTile(col, row)
    }

    fun isTowerTile(col: Int, row: Int) : Boolean {
        return gameMap.isTowerTile(col, row)
    }

    fun isRoadTile(col: Int, row: Int) : Boolean {
        return gameMap.isRoadTile(col, row)
    }

    fun getTileContent(col: Int, row: Int) : Int {
        return gameMap.getTileContent(col, row)
    }

    fun getPathEncoding() : List<Int> {
        return gameMap.getPathEncoding()
    }

    fun getFirstTile() : Pair<Int, Int> {
        return gameMap.getFirstTile()
    }
}