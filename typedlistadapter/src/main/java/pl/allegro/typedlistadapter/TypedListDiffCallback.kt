package pl.allegro.typedlistadapter

import androidx.recyclerview.widget.DiffUtil

internal class TypedListDiffCallback(
    private val viewHolderFactories: List<TypedListViewHolderFactory<out TypedListItem>>
    ) : DiffUtil.ItemCallback<TypedListItem>() {

    override fun areItemsTheSame(oldItem: TypedListItem, newItem: TypedListItem): Boolean {
        return findViewHolderFactory(oldItem).hasItemsTheSameClassAndAreTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: TypedListItem, newItem: TypedListItem): Boolean {
        return findViewHolderFactory(oldItem).areItemsContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: TypedListItem, newItem: TypedListItem): Any? {
        return findViewHolderFactory(oldItem).getChangeItemsPayload(oldItem, newItem)
    }

    private fun findViewHolderFactory(item: TypedListItem): TypedListViewHolderFactory<out TypedListItem> {
        return viewHolderFactories
            .firstOrNull { it.isItemCompatible(item) }
            ?: throw IllegalArgumentException("No view holder factory found for ${item::class.java}")
    }
}