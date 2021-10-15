package pl.allegro.typedlistadapter.sample.adapter

import android.view.View
import android.widget.TextView
import pl.allegro.typedlistadapter.TypedListViewHolder
import pl.allegro.typedlistadapter.TypedListViewHolderFactory
import pl.allegro.typedlistadapter.sample.R
import pl.allegro.typedlistadapter.sample.data.Car

class CarViewHolder(
    view: View,
    val onClickListener: (Car) -> Unit,
) : TypedListViewHolder<Car>(view) {

    private val brand: TextView = itemView.findViewById(R.id.brand)
    private val model: TextView = itemView.findViewById(R.id.model)

    override fun bind(item: Car) {
        brand.text = item.brand
        model.text = item.model
        itemView.setOnClickListener { onClickListener(item) }
    }

    class Factory(onClickListener: (Car) -> Unit) : TypedListViewHolderFactory<Car>(
        R.layout.item_car,
        CarViewHolder::class.java,
        createViewHolder = { CarViewHolder(it, onClickListener) }
    )
}