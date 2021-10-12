TypedListAdapter
================

Wrapper over existing `RecyclerView.Adapter`, to make developers life easier.
- supports multiple view types without dealing with `getItemViewType`!
- reuse ViewHolders across different adapters, with one line of code!
- built-in DiffUtil support!

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

Let's say, we have some domain classes that we want to show in our `RecyclerView`:

```kotlin
data class Car(val brand: String, val model: String)

data class Coffee(val name: String)
```

First, those classes need to implement a marker interface, `TypedListItem`:

```kotlin
data class Car(val brand: String, val model: String) : TypedListItem

data class Coffee(val name: String) : TypedListItem
```

Now we can create a ViewHolder for each of those classes. It must extend `TypedListViewHolder`:

```kotlin
class CarViewHolder(view: View) : TypedListViewHolder<Car>(view) {

    private val brand: TextView = itemView.findViewById(R.id.brand)
    private val model: TextView = itemView.findViewById(R.id.model)

    override fun bind(item: Car) {
        brand.text = item.brand
        model.text = item.model
    }
}

class CoffeeViewHolder(view: View) : TypedListViewHolder<Coffee>(view) {

    private val name: TextView = itemView.findViewById(R.id.name)

    override fun bind(item: Coffee) {
        name.text = item.name
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

class CoffeeViewHolder(view: View) : TypedListViewHolder<Coffee>(view) {

    // ...

    class Factory : TypedListViewHolderFactory<Coffee>(
        R.layout.item_coffee,
        CoffeeViewHolder::class.java,
        createViewHolder = { CoffeeViewHolder(it) }
    )
}

// CoffeeViewHolder
```

The last step is creating the adapter itself. For that, we need to pass a list that contains a factory
for every item that will be used in this adapter:

```kotlin
val typedListAdapter = TypedListAdapter(
        viewHolderFactories = listOf(
            CarViewHolder.Factory(),
            CoffeeViewHolder.Factory(),
        )
    )
```

And that's it, the adapter is ready to go!

```kotlin
val contentToDisplay: List<TypedListItem> = listOf(
    Car("Audi", "A8"),
    Coffee("Espresso"),
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
