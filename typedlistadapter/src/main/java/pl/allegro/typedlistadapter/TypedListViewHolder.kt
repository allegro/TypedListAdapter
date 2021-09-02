package pl.allegro.typedlistadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseTypedListViewHolder<I : TypedListItem>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: I)

    open fun bind(item: I, payloads: MutableList<Any>) {
        // nop
    }

    open fun unbind() {
        // nop
    }
}

abstract class TypedListViewHolder<I : TypedListItem>(parent: ViewGroup, @LayoutRes layoutRes: Int) :
    BaseTypedListViewHolder<I>(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))

abstract class ViewBindingTypedListViewHolder<I : TypedListItem, B : ViewBinding>(protected val binding: B) :
    BaseTypedListViewHolder<I>(binding.root)