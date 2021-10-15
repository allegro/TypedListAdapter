package pl.allegro.typedlistadapter.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.allegro.typedlistadapter.TypedListAdapter
import pl.allegro.typedlistadapter.sample.adapter.AdvertisementViewHolder
import pl.allegro.typedlistadapter.sample.adapter.CarViewHolder
import pl.allegro.typedlistadapter.sample.adapter.CoffeeViewHolder
import pl.allegro.typedlistadapter.sample.data.Advertisement
import pl.allegro.typedlistadapter.sample.data.Car
import pl.allegro.typedlistadapter.sample.data.Coffee
import pl.allegro.typedlistadapter.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySampleBinding

    private val typedListAdapter = TypedListAdapter(
        viewHolderFactories = listOf(
            CarViewHolder.Factory { sayVroom(it) },
            CoffeeViewHolder.Factory(),
            AdvertisementViewHolder.Factory()
        )
    )

    private val contentToDisplay = listOf(
        Car(brand = "Seat", model = "Ibiza"),
        Car(brand = "Fiat", model = "Bravo"),
        Coffee(name = "Latte"),
        Advertisement,
        Coffee(name = "Americano"),
        Advertisement,
        Car(brand = "Skoda", model = "Felicia"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sampleRecyclerView.adapter = typedListAdapter

        typedListAdapter.submitList(contentToDisplay)
    }

    private fun sayVroom(car: Car) {
        Toast.makeText(this, "$car says: VROOOOM!!!", Toast.LENGTH_SHORT).show()
    }
}