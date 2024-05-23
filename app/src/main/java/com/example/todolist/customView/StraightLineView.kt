package com.example.todolist.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class StraightLineView: View {
    private val paint = Paint()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        paint.color = resources.getColor(android.R.color.black) // Set line color
        paint.strokeWidth = 5f // Set line width
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Get view width and height
        val width = width.toFloat()
        val height = height.toFloat()
        // Draw a straight line from top-left corner to bottom-right corner
        canvas.drawLine(0f, 0f, width, height, paint)
    }
}