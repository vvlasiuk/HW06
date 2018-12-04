package net.ukr.vlsv.hw06

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_weather_detail.*
import net.ukr.vlsv.hw06.adapters.WeatherAdapter
import net.ukr.vlsv.hw06.data.DailyWeather
import net.ukr.vlsv.hw06.data.DailyWeatherList
import java.text.SimpleDateFormat

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        val df = SimpleDateFormat("dd.MM.yyyy hh:mm")

        val weatherDate = intent.getLongExtra(MainActivity.DETAIL_KEY + "_dt", 0).times(1000)
        val weatherTemp = getTemp(intent.getDoubleExtra(MainActivity.DETAIL_KEY + "_temp", 0.0))
        val weatherWind = intent.getDoubleExtra(MainActivity.DETAIL_KEY + "_wind", 0.0).toInt()
        val weatherPres = intent.getDoubleExtra(MainActivity.DETAIL_KEY + "_pres", 0.0).toInt()
        val weatherWeath = intent.getStringExtra(MainActivity.DETAIL_KEY + "_weath")

        TextView_DetailDate.text = "Date: ${df.format(weatherDate)}"
        TextView_DetailTemp.text = "Temp: $weatherTemp °${MainActivity.TEMP_UNIT}"
        TextView_DetailWind.text = "Wind: $weatherWind"
        TextView_DetailPres.text = "Pressure: $weatherPres"
        TextView_DetailWeath.text = "Precipitation: $weatherWeath"

        ButtonDetailBack.setOnClickListener {
            finish()
        }
    }

    fun getTemp(temp: Double): String {
        var mTemp: Double = temp

        if (MainActivity.TEMP_UNIT == "C") {mTemp = temp - 273.15}

        return mTemp.toInt().toString()
    }
}
