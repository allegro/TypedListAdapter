package pl.allegro.typedlistadapter.sample.adapter

import android.view.ViewGroup
import android.widget.TextView
import pl.allegro.typedlistadapter.TypedListViewHolder
import pl.allegro.typedlistadapter.TypedListViewHolderFactory
import pl.allegro.typedlistadapter.sample.R
import pl.allegro.typedlistadapter.sample.data.Car

class CarViewHolder(
    parent: ViewGroup,
    val onClickListener: (Car) -> Unit,
) : TypedListViewHolder<Car>(parent, R.layout.item_car) {

    private val brand: TextView = itemView.findViewById(R.id.brand)
    private val model: TextView = itemView.findViewById(R.id.model)

    override fun bind(item: Car) {
        brand.text = item.brand
        model.text = item.model
        itemView.setOnClickListener { onClickListener(item) }
    }

    class Factory(onClickListener: (Car) -> Unit) : TypedListViewHolderFactory<Car>(
        CarViewHolder::class.java,
        createViewHolder = { CarViewHolder(it, onClickListener) }
    )
}