package com.example.towerdefense.model

import kotlin.math.sqrt

class Projectile(col: Int, row: Int, projectileType: ProjectileType, projectileRadius: Int, target: AttackListener, visibility: Boolean, damage: Int) : Body(col, row) {
    //**************************************** Variables **************************************** //

    private val mProjectileType = projectileType
    private val mTarget = target

    private var mStartX : Int = GameMapUtils.gridToPixel(col) + (GameMapUtils.PX_PER_TILE/2)
    private var mStartY : Int = GameMapUtils.gridToPixel(row) + (GameMapUtils.PX_PER_TILE/2)
    private val mFinalX : Int = mTarget.getNextRealPos().first + (GameMapUtils.PX_PER_TILE/2)
    private val mFinalY : Int = mTarget.getNextRealPos().second + (GameMapUtils.PX_PER_TILE/2)

    private var mDx: Int = 0
    private var mDy: Int = 0
    private val mRadius : Int = projectileRadius


    private var mTargetAttacked = false
    private val speedPerTick = 50
    private val mAttackTick: Int = 1
    private var mActualTick: Int = 0
    private val mDamage: Int = damage
    private val mVisibility = visibility

    //*************************************** Constructor *************************************** //

    init {

        calculateAttackTick()
        distanceToTarget()

    }

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        // !!!SB: Implementer
        if(mActualTick<=0){
            mTarget.onAttack(mDamage)
            mTargetAttacked=true
        } else{
            mActualTick-=1
        }
    }

    override fun advanceDisplayTick() {
        if(mActualTick>0) {
            updatePosition()
        }
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

    //************************************* Private methods ************************************* //

    private fun calculateAttackTick() {
        //mAttackTick=distanceToTarget().toInt()/speedPerTick
        mActualTick=mAttackTick
    }

    private fun distanceToTarget(): Double {
        mDx = mFinalX-mStartX
        mDy = mFinalY-mStartY
        return sqrt(((mDx * mDx)+(mDy * mDy).toDouble()))
    }

    private fun updatePosition(){



        val realX: Int = this.getRealX()
        val realY: Int = this.getRealY()
        //val newX:Int = realX + (mDx/(mAttackTick * mGameTimerView.getTickRatio()) + 0.5*GameMapUtils.PX_PER_TILE).toInt()
        //val newY:Int = realY + (mDy/(mAttackTick * mGameTimerView.getTickRatio()) + 0.5*GameMapUtils.PX_PER_TILE).toInt()

        val newX:Int = (mFinalX-mStartX)/(mAttackTick * mGameTimerView.getTickRatio()) + this.getRealX()
        val newY:Int = (mFinalY-mStartY)/(mAttackTick * mGameTimerView.getTickRatio()) + this.getRealY()

        //val newX:Int = mTarget.getNextRealPos().first + (GameMapUtils.PX_PER_TILE/2)
        //val newY:Int = mTarget.getNextRealPos().second + (GameMapUtils.PX_PER_TILE/2)

        setRealX(newX)
        setRealY(newY)

    }

}