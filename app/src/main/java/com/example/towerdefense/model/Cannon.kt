package com.example.towerdefense.model

class Cannon(col: Int, row: Int) : Tower(col, row) {
    //**************************************** Variables **************************************** //
    private var mBlastRadius : Int = 100
    //*************************************** Constructor *************************************** //
    init {
        mCost = 1000
        mDamage = 5
        mProjectileRadius = 20

    }
    //************************************* Public methods ************************************** //
    override fun advanceMainTick() {
        super.advanceMainTick()
    }

    override fun advanceDisplayTick() {
        // !!!SB: Implementer
        super.advanceDisplayTick()
    }

    override fun findEnemiesInBlastRadius(enemies: List<AttackListener>) {

        mInBlastRadius.clear()

        for (e in enemies) {
            if (mTarget != e && isInBlastRadius(e)) {
                mInBlastRadius.add(e)
            }
        }
    }

    override fun createProjectile() {
        if(mTarget != null){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.CANNON_PROJECTILE, mProjectileRadius, mTarget!!,true,mDamage))
        }
        for (e in mInBlastRadius){
            mProjectiles.add(Projectile(this.getRealX(), this.getRealY(),
                ProjectileType.CANNON_PROJECTILE, mProjectileRadius, e,false,mDamage))

        }
    }


    //************************************* Private methods ************************************* //
    private fun isInBlastRadius(enemy: AttackListener) : Boolean{

        if(mTarget != null){

            val dx = enemy.getPosX() - mTarget?.getPosX()!!
            val dy = enemy.getPosY() - mTarget?.getPosY()!!
            val dx2 = dx * dx
            val dy2 = dy * dy
            return ((dx2 + dy2) < (mBlastRadius * mBlastRadius))
        }
        return false
    }
}