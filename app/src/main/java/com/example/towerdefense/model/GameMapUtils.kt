package com.example.towerdefense.model

class GameMapUtils {
    companion object {
        const val N_ROWS: Int = 8
        const val N_COLUMNS: Int = 16
        var PX_PER_TILE = 80

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

        fun gridToCenterPixel(px : Int, py: Int): Pair<Int, Int>{
            return Pair(gridToCenterPixel(px),gridToCenterPixel(py))
        }

        fun gridToCenterPixel(grid: Int): Int{
            return gridToPixel(grid) + PX_PER_TILE/2
        }

        fun isValidRow(row: Int) : Boolean {
            return row in 0..<N_ROWS
        }

        fun isValidCol(col: Int) : Boolean {
            return col in 0..<N_COLUMNS
        }
    }

}