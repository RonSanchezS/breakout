package com.moviles.practicadibujotouch.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.moviles.practicadibujotouch.R
import com.moviles.practicadibujotouch.modelos.Cuadrado
import com.moviles.practicadibujotouch.ui.components.MyCanvas
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var myCanvas: MyCanvas


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCanvas = findViewById(R.id.myCanvas)

        setupListeners()

        //get screen width and height
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels

        val listaCuadrados = mutableListOf<Cuadrado>()
        //set random color
        val random = Random()





        for (y in 0..5) {
            val color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
            for (i in 0..5) {
                listaCuadrados.add(Cuadrado((i * (width/6f)+50f), (y * 75f), width/10f, 50f, color))
            }
        }

        myCanvas.agregarCuadrado(listaCuadrados)
        //create a thread and move every 1000ms
        val thread = Thread {
            while (true) {
                Thread.sleep(16)
                //myCanvas.moverCuadrados()
                myCanvas.moverPelota()

            }
        }
        thread.start()
    }

    private fun setupListeners() {
        myCanvas.setOnClickListener {

        }
    }
}