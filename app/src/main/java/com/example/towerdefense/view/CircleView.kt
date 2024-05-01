import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CircleView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    //**************************************** Variables **************************************** //
    private var mPosX = 0
    private var mPosY = 0
    private var mRadius = 50
    private var paint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        alpha = 255 // Set initial alpha value
    }

    //*************************************** Constructor *************************************** //

    //************************************* Public methods ************************************** //
    fun getColor(): Int {
        return paint.color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(mPosX.toFloat(), mPosY.toFloat(), mRadius.toFloat(), paint)
    }

    fun setCirclePosition(x: Int, y: Int) {
        mPosX = x
        mPosY = y
        invalidate() // Redraw the view
    }

    fun setCircleAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidate() // Redraw the view
    }

    fun setCircleAttributes(x: Int, y: Int, radius: Int, color: Int) {
        mPosX = x
        mPosY = y
        mRadius = radius
        paint.color = color

        invalidate() // Redessiner la vue avec les nouvelles valeurs
    }


    //************************************* private functions************************************ //
}
