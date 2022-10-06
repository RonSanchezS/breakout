package com.moviles.practicadibujotouch.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.moviles.practicadibujotouch.modelos.Cuadrado
import com.moviles.practicadibujotouch.modelos.Pelota

class MyCanvas(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val objPaint: Paint = Paint()

    var bitmap: Bitmap? = null

//aqui va la logica

    private var puntuacion = 0

    private var pelota: Pelota = Pelota(400f, 1400f, 100f, 100f, 100f)

    private var game = true

    var listaCuadrados: MutableList<Cuadrado> = mutableListOf(
    )

    private var barra = Cuadrado(500f, 1800f, 200f, 50f, Color.RED)
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    }


     fun agregarCuadrado(lista: MutableList<Cuadrado>) {
        listaCuadrados = lista

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        objPaint.strokeWidth = 10f
        objPaint.color = Color.RED

        canvas?.drawOval(
            pelota.x,
            pelota.y,
            pelota.x + pelota.radio,
            pelota.y + pelota.radio,
            objPaint
        )

        canvas?.drawRect(
            barra.x,
            barra.y,
            barra.x + barra.lado - 50,
            barra.y + barra.alto,
            objPaint
        )


        objPaint.color = Color.RED


        for (cuadrado in listaCuadrados) {
            objPaint.color = cuadrado.color
            canvas?.drawRect(
                cuadrado.x,
                cuadrado.y,
                cuadrado.x + cuadrado.lado,
                cuadrado.y + cuadrado.alto,
                objPaint
            )
        }
        objPaint.color = Color.BLACK
        objPaint.textSize = 75f


        bitmap?.let {
            canvas?.drawBitmap(it, 0f, 0f, objPaint)
        }
        if (listaCuadrados.size == 0) {
            canvas?.drawText("No hay cuadrados, Ganaste", 100f, 300f, objPaint)
        }
        canvas?.drawText("Puntuacion: $puntuacion", 100f, 1100f, objPaint)
        if (!game) {
            canvas?.drawText("Perdiste", 100f, 1000f, objPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //check if touch is on left or right side

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {
                if (!game) {
                    restaurarPelota()
                } else {
                    barra.x = event.x
                }
            }
            MotionEvent.ACTION_UP -> {
                // bitmap = getBitmapFromView(this)

            }
            MotionEvent.ACTION_MOVE -> {
                if (game) {
                    barra.x = event.x - 50
                    invalidate()
                }

            }
        }
        return true
    }

    private fun restaurarPelota() {
        movimientoX = 0f
        movimientoY = -10f
        pelota.x = 400f
        pelota.y = 1400f
        game = true
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        val bitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }



    private var movimientoX = 0f
    private var movimientoY = -10f

    fun moverPelota() {
        //movemos la pelota
        /////////////////////////////////////
        if (pelota.y >= 2000f) {
            game = false
            invalidate()
        }
        if (game) {
            pelota.x += movimientoX
            pelota.y += movimientoY

            if (pelota.x + pelota.radio >= width || pelota.x <= 0) {
                movimientoX *= -1
            }
            if (pelota.y <= 0) {
                movimientoY = +10f
                if (movimientoX == 0f) {
                    val random = (-1..1).random()
                    if (random > 0) {
                        movimientoX = +10f
                    } else {
                        movimientoX = -10f
                    }
                }


            }

            if (pelota.y + pelota.radio >= barra.y && pelota.x + pelota.radio >= barra.x && pelota.x <= barra.x + barra.lado - 50) {
                if (pelota.y + pelota.radio <= barra.y + barra.alto && pelota.x <= barra.x + barra.lado) {
                    movimientoY = -10f
                }
            }
            //////////////////////////////////////
            var porEliminar: Cuadrado? = null
            //check for colition with cuadrado
            for (cuadrado in listaCuadrados.iterator()) {
                if (pelota.x + pelota.radio + 10 >= cuadrado.x && pelota.x <= cuadrado.x + cuadrado.lado) {
                    if (pelota.y + pelota.radio + 10 >= cuadrado.y && pelota.y <= cuadrado.y + cuadrado.alto) {
                        porEliminar = cuadrado
                        if (pelota.y > cuadrado.y) {
                            movimientoY = 10f
                        } else if (pelota.y + pelota.radio/2 >= cuadrado.y) {
                            movimientoY = +10f
                        }else{
                            movimientoY = -10f
                        }
                        if (pelota.x+pelota.radio/2 > cuadrado.x+cuadrado.lado/2) {
                            movimientoX = 10f
                        } else {
                            movimientoX = -10f
                        }

                    }
                }
            }

            if (porEliminar != null) {
                listaCuadrados.remove(porEliminar)
                puntuacion++
            }


            invalidate()

//        for (cuadrado in listaCuadrados) {
//            if (pelota.x + pelota.radio >= cuadrado.x && pelota.x <= cuadrado.x + cuadrado.lado) {
//                if (pelota.y + pelota.radio >= cuadrado.y && pelota.y <= cuadrado.y + cuadrado.alto) {
//                    movimientoX *= -1
//                    movimientoY *= -1
//                }
//            }
//        }
        }
    }

}