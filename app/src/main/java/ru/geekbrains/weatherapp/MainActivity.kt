package ru.geekbrains.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.weatherapp.databinding.MainActivityBinding
import ru.geekbrains.weatherapp.databinding.MainFragmentBinding
import ru.geekbrains.weatherapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(getLayoutInflater())
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}