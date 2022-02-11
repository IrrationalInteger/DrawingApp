package com.example.myapplication

import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import com.example.myapplication.Drawing.Companion.currentBrush

class MainActivity : AppCompatActivity() {


    companion object {
        var path = Path()
        var paintBrush = Paint()
        var mode = "L"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val drawL = findViewById<ImageButton>(R.id.drawLine)
        val drawA = findViewById<ImageButton>(R.id.drawArrow)
        val drawS = findViewById<ImageButton>(R.id.drawSquare)
        val drawE = findViewById<ImageButton>(R.id.drawEllipse)
        val selC = findViewById<ImageButton>(R.id.selectColor)
        val redC = findViewById<ImageButton>(R.id.redColor)
        val blueC = findViewById<ImageButton>(R.id.blueColor)
        val greenC = findViewById<ImageButton>(R.id.greenColor)
        val blackC = findViewById<ImageButton>(R.id.blackColor)
        var currentColor:String = "Red"
        drawL.setOnClickListener{
            mode = "L"
            turnInvis(findViewById(R.id.layout2))
        }
        drawA.setOnClickListener{
            mode = "A"
            turnInvis(findViewById(R.id.layout2))

        }
        drawS.setOnClickListener{
            mode = "S"
            turnInvis(findViewById(R.id.layout2))

        }
        drawE.setOnClickListener{
            mode = "E"
            turnInvis(findViewById(R.id.layout2))

        }
        selC.setOnClickListener{
            toggleView(findViewById(R.id.layout2))
        }
        redC.setOnClickListener{
           paintBrush.color = getResources().getColor(R.color.red)
            currentColor(paintBrush.color)
            toggleView(findViewById(R.id.layout2))

        }
        blueC.setOnClickListener{
            paintBrush.color = getResources().getColor(R.color.blue)
            currentColor(paintBrush.color)
            toggleView(findViewById(R.id.layout2))

        }
        greenC.setOnClickListener{
            paintBrush.color = getResources().getColor(R.color.green)
            currentColor(paintBrush.color)
            toggleView(findViewById(R.id.layout2))

        }
        blackC.setOnClickListener{
            paintBrush.color = getResources().getColor(R.color.black)
            currentColor(paintBrush.color)
            toggleView(findViewById(R.id.layout2))

        }



    }
    private fun currentColor(color: Int){
        currentBrush = color
        path = Path()
    }
    private fun toggleView(view: View) {
        view.isVisible = !view.isVisible
    }
    private fun turnInvis(view: View) {
        view.isVisible = false;
    }
}