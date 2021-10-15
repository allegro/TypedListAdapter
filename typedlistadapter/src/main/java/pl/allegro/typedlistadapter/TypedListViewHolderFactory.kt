package pl.allegro.typedlistadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import java.lang.reflect.ParameterizedType

typealias ItemCompatibilityChecker<I> = (expectedItemClass: Class<I>, item: TypedListItem) -> Boolean

open class TypedListViewHolderFactory<I : TypedListItem>(
    @LayoutRes private val layoutId: Int,
    private val viewHolderClass: Class<out TypedListViewHolder<I>>,
    val createViewHolder: (itemView: View) -> TypedListViewHolder<I>,
    val areItemsTheSame: (oldItem: I, newItem: I) -> Boolean = { _, _ -> true },
    val areContentsTheSame: (oldItem: I, newItem: I) -> Boolean = { old, new -> old == new },
    val getChangePayload: (oldItem: I, newItem: I) -> Any? = { _, _ -> null },
    private val isItemCompatible: ItemCompatibilityChecker<I> = compareClassItemCompatibilityChecker
) {

    @Suppress("UNCHECKED_CAST")
    private val itemClass: Class<I> =
        (viewHolderClass.genericSuperclass as ParameterizedType).actualTypeArguments.first() as Class<I>

    internal fun onCreateViewHolder(parent: ViewGroup): TypedListViewHolder<I> {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(layoutId, parent, false)
        return createViewHolder(itemView)
    }

    internal fun isItemCompatible(item: TypedListItem) = isItemCompatible(itemClass, item)

    internal fun hasItemsTheSameClassAndAreTheSame(
        oldItem: TypedListItem,
        newItem: TypedListItem
    ): Boolean {
        if (oldItem::class.java == newItem::class.java) {
            return areItemsTheSame(itemClass.cast(oldItem)!!, itemClass.cast(newItem)!!)
        }

        return false
    }

    internal fun areItemsContentsTheSame(oldItem: TypedListItem, newItem: TypedListItem): Boolean {
        if (hasItemsTheSameClassAndAreTheSame(oldItem, newItem)) {
            return areContentsTheSame(itemClass.cast(oldItem)!!, itemClass.cast(newItem)!!)
        }

        return false
    }

    internal fun getChangeItemsPayload(oldItem: TypedListItem, newItem: TypedListItem): Any? {
        if (hasItemsTheSameClassAndAreTheSame(oldItem, newItem)) {
            return getChangePayload(itemClass.cast(oldItem)!!, itemClass.cast(newItem)!!)
        }

        return null
    }

    internal fun bind(item: TypedListItem, viewHolder: TypedListViewHolder<*>) {
        viewHolderClass.cast(viewHolder)!!.bind(itemClass.cast(item)!!)
    }

    internal fun bind(
        item: TypedListItem,
        viewHolder: TypedListViewHolder<*>,
        payloads: MutableList<Any>
    ) {
        viewHolderClass.cast(viewHolder)!!.bind(itemClass.cast(item)!!, payloads)
    }

    companion object {
        @JvmStatic
        val compareClassItemCompatibilityChecker =
            { itemClass: Class<*>, item: TypedListItem -> itemClass == item::class.java }
    }
}