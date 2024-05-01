package com.example.towerdefense.view

import CircleView
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import com.example.towerdefense.model.Body
import com.example.towerdefense.model.GameMapUtils
import com.example.towerdefense.model.Projectile

class DrawableBody(private val mBody: Body, private val mView: View) {
    fun getBody() : Body { return this.mBody }
    fun getView() : View { return this.mView }

    fun updateImage() {
        if (mView is CircleView && mBody is Projectile) {
            val circleView = mView
            val p = mBody
            circleView.setCircleAttributes(mBody.getRealX(), mBody.getRealY(), p.getRadius(), p.getColor())

        } else {
            val params = FrameLayout.LayoutParams(GameMapUtils.PX_PER_TILE, GameMapUtils.PX_PER_TILE)
            params.leftMargin = mBody.getRealX()
            params.topMargin = mBody.getRealY()

            // Set layout parameters
            mView.layoutParams = params
        }

        mView.invalidate()
    }
}