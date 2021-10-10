package pl.allegro.typedlistadapter.sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pl.allegro.typedlistadapter.TypedListAdapter
import pl.allegro.typedlistadapter.sample.adapter.AdvertisementViewHolder
import pl.allegro.typedlistadapter.sample.adapter.CarViewHolder
import pl.allegro.typedlistadapter.sample.adapter.CoffeeViewHolder
import pl.allegro.typedlistadapter.sample.data.Car
import pl.allegro.typedlistadapter.sample.data.ContentToDisplay
import pl.allegro.typedlistadapter.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySampleBinding

    private val typedListAdapter = createAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.sampleRecyclerView) {
            adapter = typedListAdapter
        }
        typedListAdapter.submitList(ContentToDisplay())
    }

    private fun createAdapter() = TypedListAdapter(
        viewHolderFactories = listOf(
            CarViewHolder.Factory { sayVroom(it) },
            CoffeeViewHolder.Factory(),
            AdvertisementViewHolder.Factory()
        )
    )

    private fun sayVroom(car: Car) {
        Toast.makeText(this, "$car says: VROOOOM!!!", Toast.LENGTH_SHORT).show()
    }
}