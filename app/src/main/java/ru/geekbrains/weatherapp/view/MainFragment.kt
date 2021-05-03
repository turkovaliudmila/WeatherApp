package ru.geekbrains.weatherapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geekbrains.weatherapp.R
import ru.geekbrains.weatherapp.databinding.MainFragmentBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import ru.geekbrains.weatherapp.model.AppState
import ru.geekbrains.weatherapp.model.Weather
import ru.geekbrains.weatherapp.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getWeatherFromLocalSource()

    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val wheatherData = appState.weatherData
                binding.loadingLayout.visibility = View.GONE;
                setData(wheatherData)
                Snackbar.make(binding.mainView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE;
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE;
                Snackbar
                    .make(binding.mainView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getWeatherFromLocalSource() }
                    .show()
            }
        }
    }

    private fun setData(weatherData: Weather) {
        binding.cityName.text = weatherData.City.city
        binding.cityCoordinates.text = String.format(getString(R.string.city_coordinates), weatherData.City.lat.toString(), weatherData.City.lon.toString())
        binding.temperatureValue.text = weatherData.temperature.toString()
        binding.feelsLikeValue.text = weatherData.feelsLike.toString()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}