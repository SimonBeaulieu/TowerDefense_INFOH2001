package com.example.towerdefense.model

import android.graphics.Color
import kotlin.math.sqrt

class Projectile(col: Int, row: Int, projectileType: ProjectileType, projectileRadius: Int,
                 target: AttackListener, visibility: Boolean, damage: Int, color : Int = Color.RED) : Body(col, row) {
    //**************************************** Variables **************************************** //

    private val mProjectileType = projectileType
    private val mTarget = target

    private var mStartX : Int = GameMapUtils.gridToCenterPixel(col)
    private var mStartY : Int = GameMapUtils.gridToCenterPixel(row)
    private val mFinalX : Int = mTarget.getNextRealPos().first + (GameMapUtils.PX_PER_TILE/2)
    private val mFinalY : Int = mTarget.getNextRealPos().second + (GameMapUtils.PX_PER_TILE/2)

    private val mRadius : Int = projectileRadius
    private val mColor : Int = color

    private var mTargetAttacked = false
    private val mDamage: Int = damage
    private val mVisibility = visibility

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        mTarget.onAttack(mDamage)
        mTargetAttacked=true
    }

    override fun advanceDisplayTick() {
        updatePosition()
    }

    fun isVisible(): Boolean{
        return mVisibility
    }

    fun hasReachedTarget() : Boolean {
        return mTargetAttacked
    }

    fun getType() : ProjectileType{
        return mProjectileType
    }

    fun getRadius():Int{
        return mRadius
    }

    fun getColor() : Int {
        return mColor
    }

    //************************************* Private methods ************************************* //
    private fun updatePosition(){

        val tickFraction: Double = mGameTimerView.getTickFraction()

        val newX:Int = ((mFinalX-mStartX)*tickFraction).toInt() + mStartX
        val newY:Int = ((mFinalY-mStartY)*tickFraction).toInt() + mStartY

        setRealX(newX)
        setRealY(newY)
    }
}