package com.example.myapplication

import android.R.attr
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.MainActivity.Companion.mode
import com.example.myapplication.MainActivity.Companion.paintBrush
import com.example.myapplication.MainActivity.Companion.path
import java.lang.Exception
import java.lang.Math.*


class Drawing: View {

    var params : ViewGroup.LayoutParams? = null
    companion object {
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var Arrows = ArrayList<Arrow>()
        var Squares = ArrayList<Square>()
        var Ellipses = ArrayList<Ellipse>()
        var indexOfArrows = ArrayList<Int>()
        var currentBrush = Color.rgb(251,0,8)
    }
    constructor(context: Context) : this(context, null){
        init()
}
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f
        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y
        if(mode =="L"){
        when (event.action){
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y)
                return true;
            }
            MotionEvent.ACTION_MOVE ->{
                path.lineTo(x,y)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else -> return false
        }
        postInvalidate()
        return false
            }
        else if (mode =="A"){
            var curr = Arrow()
            when (event.action){
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(x,y)
                    curr.startX = x
                    curr.startY = y
                    Arrows.add(curr)
                    return true;
                }
                MotionEvent.ACTION_UP ->{
                    path.lineTo(x,y)
                    pathList.add(path)
                    Arrows.last().endY = y
                    Arrows.last().endX = x
                    indexOfArrows.add(pathList.size-1)
                    colorList.add(currentBrush)
                }
                else -> return false
            }
            postInvalidate()
            return false
        }
        else if (mode == "S"){
            var curr = Square()
            when (event.action){
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(x,y)
                    curr.startX = x
                    curr.startY = y
                    Squares.add(curr)
                    return true;
                }
                MotionEvent.ACTION_UP ->{
                    Squares.last().endY = y
                    Squares.last().endX = x
                    path.addRect(Squares.last().startX, Squares.last().startY,x,y, Path.Direction.CCW)
                    pathList.add(path)
                    colorList.add(currentBrush)
                }
                else -> return false
            }
            postInvalidate()
            return false

        }
        else{
            var curr = Ellipse()
            when (event.action){
                MotionEvent.ACTION_DOWN -> {
                    path.moveTo(x,y)
                    curr.startX = x
                    curr.startY = y
                    Ellipses.add(curr)
                    return true;
                }
                MotionEvent.ACTION_UP ->{
                    Ellipses.last().endY = y
                    Ellipses.last().endX = x
                    path.addOval(Ellipses.last().startX,Ellipses.last().startY,x,y, Path.Direction.CCW)
                    pathList.add(path)
                    colorList.add(currentBrush)
                }
                else -> return false
            }
            postInvalidate()
            return false

        }
        return false


    }

    override fun onDraw(canvas: Canvas) {
        var j = 0
        for (i in pathList.indices){
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)
            invalidate()
        }
            for (i in pathList.indices){
            if (j < indexOfArrows.size) {
                if (indexOfArrows[j] == i) {
                    paintBrush.setColor(colorList[i])
                    canvas.drawPath(pathList[i], paintBrush)
                    drawArrow(canvas, paintBrush, Arrows[j].startX, Arrows[j].startY, Arrows[j].endX, Arrows[j].endY)
                    invalidate()
                    j++
                }
            }

        }
        invalidate()
    }
    private fun drawArrow(canvas: Canvas, paint: Paint,x:Float,y:Float,x1: Float,y1:Float) {
        val degree = calculateDegree(x, x1, y, y1)
        val endX1 = (x1 + 50 * kotlin.math.cos(Math.toRadians(degree - 30 + 90)))
        val endY1 = (y1 + 50 * kotlin.math.sin(Math.toRadians(degree - 30 + 90)))
        val endX2 = (x1 + 50 * kotlin.math.cos(Math.toRadians(degree - 60 + 180)))
        val endY2 = (y1 + 50 * kotlin.math.sin(Math.toRadians(degree - 60 + 180)))
        canvas.drawLine(x1, y1, endX1.toFloat(), endY1.toFloat(), paint)
        canvas.drawLine(x1, y1, endX2.toFloat(), endY2.toFloat(), paint)
    }

    private fun calculateDegree(x1: Float, x2: Float, y1: Float, y2: Float): Double {
        var startRadians = kotlin.math.atan(((y2 - y1) / (x2 - x1)).toDouble()).toFloat()
        startRadians += ((if (x2 >= x1) 90 else -90) * Math.PI / 180).toFloat()
        return Math.toDegrees(startRadians.toDouble())
    }
}