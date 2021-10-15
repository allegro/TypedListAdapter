package pl.allegro.typedlistadapter.sample.adapter

import android.view.View
import pl.allegro.typedlistadapter.TypedListViewHolder
import pl.allegro.typedlistadapter.TypedListViewHolderFactory
import pl.allegro.typedlistadapter.sample.R
import pl.allegro.typedlistadapter.sample.data.Coffee
import pl.allegro.typedlistadapter.sample.databinding.ItemCoffeeBinding

class CoffeeViewHolder(view: View) : TypedListViewHolder<Coffee>(view) {

    private val binding = ItemCoffeeBinding.bind(itemView)

    override fun bind(item: Coffee) {
        binding.name.text = item.name
    }

    class Factory : TypedListViewHolderFactory<Coffee>(
        R.layout.item_coffee,
        CoffeeViewHolder::class.java,
        createViewHolder = { CoffeeViewHolder(it) }
    )
}