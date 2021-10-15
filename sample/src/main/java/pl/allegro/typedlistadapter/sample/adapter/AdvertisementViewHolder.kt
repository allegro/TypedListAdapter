package pl.allegro.typedlistadapter.sample.adapter

import android.view.View
import pl.allegro.typedlistadapter.TypedListViewHolder
import pl.allegro.typedlistadapter.TypedListViewHolderFactory
import pl.allegro.typedlistadapter.sample.R
import pl.allegro.typedlistadapter.sample.data.Advertisement

class AdvertisementViewHolder(view: View) : TypedListViewHolder<Advertisement>(view) {

    override fun bind(item: Advertisement) {
        // noop
    }

    class Factory : TypedListViewHolderFactory<Advertisement>(
        R.layout.item_advertisement,
        AdvertisementViewHolder::class.java,
        createViewHolder = { AdvertisementViewHolder(it) }
    )
}