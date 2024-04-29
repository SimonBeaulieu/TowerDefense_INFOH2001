package com.example.towerdefense.model


class Archer(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //
    private var numberOfExtraTargets : Int = 1
    //*************************************** Constructor *************************************** //

    init{
        mCost=500
        mRange=250
        mDamage = 5
    }

    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        super.advanceMainTick()
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    override fun findEnemiesInBlastRadius(enemies: List<AttackListener>){
        mInBlastRadius.clear()
        var pos : Pair<Int, Int>
        var tileValue : Int = 0
        var maxTileValue : Int = 0
        var extraTargets = numberOfExtraTargets
        for (e in enemies) {
            if (mTarget != e && isInRange(e) && extraTargets >= 0) {
                pos = GameMapUtils.pixelToGrid(e.getPosX(), e.getPosY())
                tileValue = mGameMapView.getTileContent(pos.first, pos.second)
                if (tileValue > maxTileValue) {
                    maxTileValue = tileValue
                    mInBlastRadius.add(e)
                    extraTargets -= 1

                }
            }
        }
    }

    override fun createProjectile() {
        if(mTarget != null){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.ARCHER_PROJECTILE, mTarget!!,true,mDamage))
        }
        for (e in mInBlastRadius){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.ARCHER_PROJECTILE, e,true,mDamage))
        }
    }

    //************************************* Private methods ************************************* //

}