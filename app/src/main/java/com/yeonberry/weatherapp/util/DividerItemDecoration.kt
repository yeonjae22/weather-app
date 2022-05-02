package com.yeonberry.weatherapp.util

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yeonberry.weatherapp.R

class DividerItemDecoration(color: Int, stroke: Float) : RecyclerView.ItemDecoration() {
    private var divider: Paint = Paint().apply {
        this.color = color
        this.strokeWidth = stroke
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        outRect.set(
            divider.strokeWidth.toInt(),
            if (position == 0) {
                divider.strokeWidth.toInt()
            } else {
                0
            },
            divider.strokeWidth.toInt(),
            0
        )
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val rowCount = parent.childCount
        for (i in 0 until rowCount) {
            val rowViewGroup = parent.getChildAt(i) as ViewGroup
            if (parent.adapter?.getItemViewType(i) == R.layout.item_title) {
                c.drawLine(
                    rowViewGroup.left.toFloat(),
                    rowViewGroup.top.toFloat(),
                    rowViewGroup.right.toFloat(),
                    rowViewGroup.top.toFloat(),
                    divider
                )
            }

            c.drawLine(
                rowViewGroup.left.toFloat(),
                rowViewGroup.bottom.toFloat(),
                rowViewGroup.right.toFloat(),
                rowViewGroup.bottom.toFloat(),
                divider
            )

            val cellCount = rowViewGroup.childCount
            for (j in 0 until rowViewGroup.childCount) {
                val cell = rowViewGroup.getChildAt(j)
                c.drawLine(
                    cell.left.toFloat() + divider.strokeWidth,
                    rowViewGroup.top.toFloat(),
                    cell.left.toFloat() + divider.strokeWidth,
                    rowViewGroup.bottom.toFloat(),
                    divider
                )

                if (j == cellCount - 1) {
                    c.drawLine(
                        rowViewGroup.right - divider.strokeWidth,
                        rowViewGroup.top.toFloat(),
                        rowViewGroup.right - divider.strokeWidth,
                        rowViewGroup.bottom.toFloat(),
                        divider
                    )
                }
            }
        }
    }
}