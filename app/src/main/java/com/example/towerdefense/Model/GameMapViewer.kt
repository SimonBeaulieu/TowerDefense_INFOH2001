package com.example.towerdefense.model

interface GameMapViewer {
    //************************************* Read methods ************************************** //
    fun isEmptyTile(col : Int, row : Int) : Boolean

    fun isTowerTile(col: Int, row: Int) : Boolean

    fun isRoadTile(col: Int, row: Int) : Boolean

    fun getTileContent(col: Int, row: Int) : Int

    fun getWaveEnemies(n: Int): List<Enemy>

    fun getPathEncoding() : List<Int>

    fun getFirstTile() : Pair<Int, Int>
}