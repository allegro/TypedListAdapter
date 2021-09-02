package pl.allegro.typedlistadapter.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.allegro.typedlistadapter.sample.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}