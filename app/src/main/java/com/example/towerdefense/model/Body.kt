package com.example.towerdefense.model

import com.example.towerdefense.model.service.GameMapReadService
import com.example.towerdefense.model.service.GameTimerReadService
import com.example.towerdefense.model.service.ServiceLocator

abstract class Body(col: Int = 0, row: Int = 0) {
    protected val mGameMapView : GameMapReadService = ServiceLocator.getService(GameMapReadService::class.java)
    protected val mGameTimerView : GameTimerReadService = ServiceLocator.getService(GameTimerReadService::class.java)

    //**************************************** Variables **************************************** //
    private var mRealX: Int = GameMapUtils.gridToPixel(col)
    private var mRealY: Int = GameMapUtils.gridToPixel(row)

    private var mGridX: Int = col
    private var mGridY: Int = row

    //*************************************** Constructor *************************************** //


    //************************************* Public methods ************************************* //
    fun getRealX(): Int { return mRealX }
    fun getRealY(): Int { return mRealY }
    fun getGridX(): Int { return mGridX }
    fun getGridY(): Int { return mGridY }

    fun setRealX(value: Int) {
        mRealX = value
        //mGridX = GameMapUtils.pixelToGrid(mRealX)
    }

    fun setRealY(value: Int) {
        mRealY = value
        //mGridY = GameMapUtils.pixelToGrid(mRealY)
    }

    fun setGridX(value: Int) {
        if (GameMapUtils.isValidCol(value)) {
            mGridX = value
            //mRealX = GameMapUtils.gridToPixel(value)
        }
    }

    fun setGridY(value: Int) {
        if (GameMapUtils.isValidRow(value)) {
            mGridY = value
            //mRealY = GameMapUtils.gridToPixel(value)
        }
    }

    abstract fun advanceMainTick()
    abstract fun advanceDisplayTick()

    //************************************* Private methods ************************************* //
}