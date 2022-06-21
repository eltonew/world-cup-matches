package my.projetc.worldcupmatches.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import my.projetc.worldcupmatches.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}