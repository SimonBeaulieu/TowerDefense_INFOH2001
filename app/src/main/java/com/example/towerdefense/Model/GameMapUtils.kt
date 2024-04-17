package com.example.towerdefense.model

class GameMapUtils {
    companion object {
        const val N_ROWS: Int = 8
        const val N_COLUMNS: Int = 16
        const val PX_PER_TILE = 120

        fun gridToPixel(gridPos:Int): Int {
            return gridPos * PX_PER_TILE
        }

        fun gridToPixel(col: Int, row: Int): Pair<Int, Int>{
            return Pair(gridToPixel(col), gridToPixel(row))
        }

        fun pixelToGrid(pxl: Int) : Int {
            return (pxl.floorDiv(PX_PER_TILE))
        }

        fun pixelToGrid(px : Int, py: Int): Pair<Int, Int>{
            return Pair(pixelToGrid(px),pixelToGrid(py))
        }

        fun isValidRow(row: Int) : Boolean {
            return row in 0..<N_ROWS
        }

        fun isValidCol(col: Int) : Boolean {
            return col in 0..<N_COLUMNS
        }
    }

}