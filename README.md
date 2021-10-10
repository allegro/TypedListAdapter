TypedListAdapter
================

Wrapper over existing `RecyclerView.Adapter`, to make developers life easier.
- supports multiple view types without dealing with `getItemViewType`!
- reuse ViewHolders across different adapters, with one line of code!
- build-in DiffUtil support!

Usage
-----

Add `TypedListAdapter` as dependency:

```
repositories {
    mavenCentral()
}

dependencies {
    TODO
}
```

Lets say, we have some domain class that we want to show in our `RecyclerView`:

```kotlin
data class Car(val brand: String, val model: String)
```

First, we must extend this class with marker interface, `TypedListItem`:

```kotlin
data class Car(val brand: String, val model: String) : TypedListItem
```

Now we can create a ViewHolder for this class. It must extend `TypedListViewHolder`:

```kotlin
class CarViewHolder(view: View) : TypedListViewHolder<Car>(view) {

    private val brand: TextView = itemView.findViewById(R.id.brand)
    private val model: TextView = itemView.findViewById(R.id.model)

    override fun bind(item: Car) {
        brand.text = item.brand
        model.text = item.model
    }
}
```

Every `TypedListViewHolder` needs a static factory class. So let's create one inside it:

```kotlin
class CarViewHolder(view: View) : TypedListViewHolder<Car>(view) {

    // ...

    class Factory : TypedListViewHolderFactory<Car>(
        R.layout.item_car,
        CarViewHolder::class.java,
        createViewHolder = { CarViewHolder(it) }
    )
}
```

The last step is creating the adapter itself. For that, we need to pass a list that contains a factory
for every item that will be used in this adapter:

```kotlin
val typedListAdapter = TypedListAdapter(
        viewHolderFactories = listOf(
            CarViewHolder.Factory(),
            // ...
        )
    )
```

And that's it, the adapter is ready to go!

```kotlin
val contentToDisplay: List<TypedListItem> = listOf(
    Car("Audi", "A8"),
    Car("Alfa Romeo", "Giula"),
    // ...
)

typedListAdapter.submitList(contentToDisplay)
```

License
-------

Copyright 2021 Allegro Group

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
