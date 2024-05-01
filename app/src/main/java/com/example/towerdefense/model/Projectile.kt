package com.example.towerdefense.model

import android.graphics.Color

class Projectile(col: Int, row: Int, projectileType: ProjectileType, projectileRadius: Int,
                 target: AttackListener, visibility: Boolean, damage: Int, color : Int = Color.RED) : Body(col, row) {
    //**************************************** Variables **************************************** //

    private val mProjectileType = projectileType
    private val mTarget = target

    private var mStartX : Int = GameMapUtils.gridToCenterPixel(col)
    private var mStartY : Int = GameMapUtils.gridToCenterPixel(row)
    private val mFinalX : Int = mTarget.getNextRealPos().first
    private val mFinalY : Int = mTarget.getNextRealPos().second

    private val mInitialRadius : Int = projectileRadius
    private var mRadius : Int = mInitialRadius
    private val mColor : Int = color
    private var mAlpha : Int = 255

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

    fun getAlpha():Int {
        if (mProjectileType == ProjectileType.FLAMETHROWER_PROJECTILE){
            mAlpha = 128
        }
        return mAlpha
    }

    //************************************* Private methods ************************************* //
    private fun updatePosition(){
        val tickFraction: Double = mGameTimerView.getTickFraction()

        if(mProjectileType == ProjectileType.FLAMETHROWER_PROJECTILE) {

            mRadius = (mInitialRadius*tickFraction).toInt()
            setRealX(mStartX)
            setRealY(mStartY)

        } else{
            val newX:Int = ((mFinalX-mStartX)*tickFraction).toInt() + mStartX
            val newY:Int = ((mFinalY-mStartY)*tickFraction).toInt() + mStartY

            setRealX(newX)
            setRealY(newY)
        }

    }
}