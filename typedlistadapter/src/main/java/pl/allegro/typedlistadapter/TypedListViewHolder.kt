package pl.allegro.typedlistadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class TypedListViewHolder<I : TypedListItem>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: I)

    open fun bind(item: I, payloads: MutableList<Any>) {
        // nop
    }

    open fun unbind() {
        // nop
    }
}
