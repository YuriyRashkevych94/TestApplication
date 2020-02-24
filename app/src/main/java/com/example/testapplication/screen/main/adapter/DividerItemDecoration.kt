package com.example.testapplication.screen.main.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(private val height: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        parent.adapter?.let {
            if(parent.getChildAdapterPosition(view) != it.itemCount - 1) {
                outRect.bottom = height
            }
        }
    }
}