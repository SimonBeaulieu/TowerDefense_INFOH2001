package com.example.towerdefense

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import com.example.towerdefense.Model.EMPTY_TILE
import com.example.towerdefense.Model.MapGame
import com.example.towerdefense.Model.N_COLUMNS
import com.example.towerdefense.Model.N_ROWS
import com.example.towerdefense.Model.TOWER_TILE

class MainActivity : AppCompatActivity() {
    lateinit var gridLayoutMap : GridLayout;

    val mapGame : MapGame = MapGame();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayoutMap = findViewById(R.id.gridLayoutMap);
    }

    fun drawMap(m : MapGame){
        for (c in 0 until N_COLUMNS) {
            for (r in 0 until N_ROWS) {

                when (mapGame.mapGrid[c][r]) {
                    EMPTY_TILE, TOWER_TILE -> {
                        // Afficher tuile vide

                    }
                    else -> {
                        // Afficher route
                    }
                }
            }
        }
    }
}