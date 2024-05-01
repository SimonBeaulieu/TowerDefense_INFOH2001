package com.example.towerdefense.view

import android.view.View
import android.widget.FrameLayout
import com.example.towerdefense.model.Body
import com.example.towerdefense.model.GameMapUtils

class DrawableBody(private val mBody: Body, private val mImage: View) {
    fun getBody() : Body { return this.mBody }
    fun getImage() : View { return this.mImage }

    fun updateImage() {
        val params = FrameLayout.LayoutParams(GameMapUtils.PX_PER_TILE, GameMapUtils.PX_PER_TILE)
        params.leftMargin = mBody.getRealX()
        params.topMargin = mBody.getRealY()

        // Set layout parameters
        mImage.layoutParams = params
        mImage.invalidate()
    }
}