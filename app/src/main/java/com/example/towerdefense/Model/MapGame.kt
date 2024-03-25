package com.example.towerdefense.Model

class MapGame {
    val mapGrid = MutableList(N_COLUMNS) { MutableList(N_ROWS) { EMPTY_TILE } }

    init {
        var col = FIRST_TILE.first
        var row = FIRST_TILE.second

        mapGrid[col][row] = 1;

        for (i in 0 until PATH_ENCODING.size) {
            when (PATH_ENCODING[i]){
                0 -> mapGrid[++col][row] = i + 2
                1 -> mapGrid[col][--row] = i + 2
                2 -> mapGrid[--col][row] = i + 2
                3 -> mapGrid[col][++row] = i + 2
            }
        }
    }

    fun isEmptyTile(gridX: Int, gridY: Int) : Boolean {
        return mapGrid[gridX][gridY] == EMPTY_TILE;
    }

    fun isTowerTile(gridX: Int, gridY: Int) : Boolean {
        return mapGrid[gridX][gridY] == TOWER_TILE;
    }
}