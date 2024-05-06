package com.example.towerdefense.controller

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.towerdefense.R

class MainActivity : AppCompatActivity() {
    private lateinit var container: ConstraintLayout

    private lateinit var gameRoot : ConstraintLayout
    private lateinit var menuRoot : ConstraintLayout
    private lateinit var selectorRoot : ConstraintLayout

    private lateinit var gameController: GameController
    private lateinit var menuController: MenuController
    private lateinit var selectorController : SelectorController

    private lateinit var player : ExoPlayer
    private lateinit var playerView: PlayerView

    private lateinit var mediaItemMenu : MediaItem
    private lateinit var mediaItemGame : MediaItem

    private lateinit var inflater : LayoutInflater

    fun showGame() {
        playGameMusic()
        gameRoot.visibility = View.VISIBLE
        menuRoot.visibility = View.GONE
        selectorRoot.visibility = View.GONE

        gameController.resumeGame()
    }

    fun showMenu() {
        playMenuMusic()
        menuRoot.visibility = View.VISIBLE
        gameRoot.visibility = View.GONE
        selectorRoot.visibility = View.GONE

        gameController.pauseGame()
    }

    fun showSelector() {
        selectorRoot.visibility = View.VISIBLE
        menuRoot.visibility = View.GONE
        gameRoot.visibility = View.GONE

        gameController.pauseGame()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflater = LayoutInflater.from(this)

        // Container of subviews
        container = findViewById(R.id.container)

        // Load XML
        loadXML()

        // Controllers creation
        gameController = GameController(this)
        menuController = MenuController(this)
        selectorController = SelectorController(this)

        // Music
        initMusic()

        // Enable menu
        showMenu()
    }

    private fun loadXML() {
        gameRoot = getGameRoot()
        addContentView(gameRoot, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        // Inflate XML
        val temp2 = inflater.inflate(R.layout.activity_menu, null)
        menuRoot = temp2.findViewById(R.id.menuRoot)

        val temp3 = inflater.inflate(R.layout.activity_select, null)
        selectorRoot = temp3.findViewById(R.id.selectorRoot)

        // Add root elements of each XML in the mainActivity
        addContentView(menuRoot, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        addContentView(selectorRoot, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
    }

    private fun getGameRoot() : ConstraintLayout {
        val temp1 = inflater.inflate(R.layout.activity_game, null)
        return temp1.findViewById(R.id.gameRoot)
    }

    private fun initMusic() {
        player = ExoPlayer.Builder(this).build()
        playerView = PlayerView(this)
        playerView.player = player

        mediaItemMenu = MediaItem.fromUri(Uri.parse("android.resource://$packageName/${R.raw.mainmenu}"))
        mediaItemGame = MediaItem.fromUri(Uri.parse("android.resource://$packageName/${R.raw.game}"))
    }

    private fun playMenuMusic() {
        player.setMediaItem(mediaItemMenu)
        player.prepare()
        player.repeatMode = Player.REPEAT_MODE_ONE
        player.play()
    }

    private fun playGameMusic() {
        player.setMediaItem(mediaItemGame)
        player.prepare()
        player.repeatMode = Player.REPEAT_MODE_ONE
        player.play()
    }

    override fun onPause() {
        super.onPause()
        player.pause()
        gameController.pauseGame()
    }

    override fun onResume() {
        super.onResume()
        player.play()
        gameController.resumeGame()
    }
}
