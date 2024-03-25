package com.example.towerdefense.Model

import kotlin.math.roundToInt

/* ********************************************************************************************** */
/*                                          STATIC                                                */
/* ********************************************************************************************** */
val N_ROWS: Int = 8
val N_COLUMNS: Int = 12

val EMPTY_TILE = 0
val TOWER_TILE = -1

val PX_PER_TILE = 50

fun gridToPixel(gridX: Int, gridY: Int): Pair<Int, Int>{
    var px = (gridX * PX_PER_TILE + 0.5 * PX_PER_TILE).roundToInt();
    var py = (gridY * PX_PER_TILE + 0.5 * PX_PER_TILE).roundToInt();
    return Pair(px, py);
}

fun pixelToGrid(px : Int, py: Int): Pair<Int, Int>{
    var gridX = (px.floorDiv(PX_PER_TILE));
    var gridY = (py.floorDiv(PX_PER_TILE));
    return Pair(gridX,gridY);
}

// Encoding of the tile which contains a road (grid x, grid y)
val FIRST_TILE : Pair<Int, Int> = Pair(1,0);

// 0 = right
// 1 = up
// 2 = left
// 3 = down
val PATH_ENCODING = listOf<Int>(3,3,0,0,3,3,2,2,3,3,0,0,0,0,1,1,1,1,1,0,0,0,0,0,3,3,2,2,3,3,0,0,3,3);