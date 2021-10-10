package pl.allegro.typedlistadapter.sample.adapter

import android.view.LayoutInflater
import pl.allegro.typedlistadapter.TypedListViewHolderFactory
import pl.allegro.typedlistadapter.ViewBindingTypedListViewHolder
import pl.allegro.typedlistadapter.sample.data.Coffee
import pl.allegro.typedlistadapter.sample.databinding.ItemCoffeeBinding

class CoffeeViewHolder(binding: ItemCoffeeBinding) :
    ViewBindingTypedListViewHolder<Coffee, ItemCoffeeBinding>(binding) {

    override fun bind(item: Coffee) {
        with(binding) {
            name.text = item.name
        }
    }

    class Factory : TypedListViewHolderFactory<Coffee>(
        CoffeeViewHolder::class.java,
        createViewHolder = { parent ->
            val binding = ItemCoffeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            CoffeeViewHolder(binding)
        }
    )
}