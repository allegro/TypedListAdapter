package pl.allegro.typedlistadapter.sample.data

import pl.allegro.typedlistadapter.TypedListItem

data class Car(
    val brand: String,
    val model: String,
) : TypedListItem {

    override fun toString() = "$brand $model"
}
