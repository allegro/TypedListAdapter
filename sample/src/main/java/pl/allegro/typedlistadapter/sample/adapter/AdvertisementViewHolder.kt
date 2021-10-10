package pl.allegro.typedlistadapter.sample.adapter

import android.view.LayoutInflater
import pl.allegro.typedlistadapter.TypedListViewHolderFactory
import pl.allegro.typedlistadapter.ViewBindingTypedListViewHolder
import pl.allegro.typedlistadapter.sample.data.Advertisement
import pl.allegro.typedlistadapter.sample.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(binding: ItemAdvertisementBinding) :
    ViewBindingTypedListViewHolder<Advertisement, ItemAdvertisementBinding>(binding) {

    override fun bind(item: Advertisement) {
        // noop
    }

    class Factory : TypedListViewHolderFactory<Advertisement>(
        AdvertisementViewHolder::class.java,
        createViewHolder = { parent ->
            val binding = ItemAdvertisementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AdvertisementViewHolder(binding)
        }
    )
}