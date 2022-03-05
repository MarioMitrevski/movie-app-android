package movieapp.app.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val verticalSpaceSize: Int = 0,
    private val horizontalSpaceSize: Int = 0
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            top = verticalSpaceSize
            right = horizontalSpaceSize
            left = horizontalSpaceSize
        }
    }
}