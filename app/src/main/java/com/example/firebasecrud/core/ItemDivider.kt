package com.example.firebasecrud.core

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class ItemDivider(context: Context) : ItemDecoration() {
    private val divider: Drawable?

    // draws the list item dividers onto the RecyclerView
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        // calculate left/right x-coordinates for all dividers
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        // for every item but the last, draw a line below it
        for (i in 0 until parent.childCount - 1) {
            val item = parent.getChildAt(i) // get ith list item
            // calculate top/bottom y-coordinates for current divider
            val top = item.bottom + (item.layoutParams as RecyclerView.LayoutParams).bottomMargin
            val bottom = top + divider!!.intrinsicHeight

            // draw the divider with the calculated bounds
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

    // constructor loads built-in Android list item divider
    init {
        val attrs = intArrayOf(R.attr.listDivider)
        divider = context.obtainStyledAttributes(attrs).getDrawable(0)
    }
}
