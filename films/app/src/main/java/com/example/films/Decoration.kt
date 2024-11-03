package com.example.films

import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class Decoration(
    private val resources: Resources
) : RecyclerView.ItemDecoration()
{
    object Constants {
        const val COLUMN_COUNT = 3
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    )
    {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        val topBottomOffset = 20.px()
        val innerOffset = 16.px()

        if (position == 0 || position == 1 || position == 2) {
            outRect.top = topBottomOffset
        } else {
            outRect.top = innerOffset / 2
        }

        if (position == itemCount - 1
            || position == itemCount - 2 && itemCount % Constants.COLUMN_COUNT != 1
            || position == itemCount - Constants.COLUMN_COUNT && itemCount % Constants.COLUMN_COUNT == 0
            ) {
            outRect.bottom = topBottomOffset
        } else {
            outRect.bottom = innerOffset / 2
        }

        if (position % Constants.COLUMN_COUNT == 0)
        {
            outRect.left = 16.px()
            outRect.right = 16.px() / 2
        }
        else if (position % Constants.COLUMN_COUNT == 2)
        {
            outRect.left = 16.px() / 2
            outRect.right = 16.px()
        }
        else
        {
            outRect.left = 16.px() / 2
            outRect.right = 16.px() / 2
        }
    }

    private fun Int.px(): Int
    {
        val floatPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        )

        return floatPx.toInt()
    }
}