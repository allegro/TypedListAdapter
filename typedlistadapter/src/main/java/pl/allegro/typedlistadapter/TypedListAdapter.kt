package pl.allegro.typedlistadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class TypedListAdapter(
    private val viewHolderFactories: List<TypedListViewHolderFactory<out TypedListItem>>
) : ListAdapter<TypedListItem, BaseTypedListViewHolder<out TypedListItem>>(
    TypedListDiffConfigFactory(viewHolderFactories).create()
) {

    private var items: List<TypedListItem> = emptyList()

    fun getIndexOfItem(item: TypedListItem): Int {
        RecyclerView
        return items.indexOfFirst { it == item }
    }

    fun getIndexOfItem(predicate: (TypedListItem) -> Boolean): Int? {
        return items.indexOfFirst { predicate(it) }
    }

    override fun submitList(list: List<TypedListItem>?) {
        items = list ?: emptyList()
        super.submitList(list)
    }

    override fun submitList(list: List<TypedListItem>?, commitCallback: Runnable?) {
        items = list ?: emptyList()
        super.submitList(list, commitCallback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseTypedListViewHolder<out TypedListItem> {
        return viewHolderFactories[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(viewHolder: BaseTypedListViewHolder<out TypedListItem>, position: Int) {
        val viewHolderFactory = viewHolderFactories[getItemViewType(position)]
        viewHolderFactory.bind(getItem(position), viewHolder)
    }

    override fun onBindViewHolder(
        viewHolder: BaseTypedListViewHolder<out TypedListItem>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            viewHolderFactories[getItemViewType(position)].bind(getItem(position), viewHolder, payloads)
        } else {
            super.onBindViewHolder(viewHolder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return viewHolderFactories
            .indexOfFirst { it.isItemCompatible(item) }
            .takeIf { it in (viewHolderFactories.indices) }
            ?: throw IllegalArgumentException("No view holder factory found for ${item::class.java}")
    }

    override fun onViewRecycled(viewHolder: BaseTypedListViewHolder<out TypedListItem>) {
        viewHolder.unbind()
    }
}
