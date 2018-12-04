package net.ukr.vlsv.hw06.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.weather_item.view.*
import net.ukr.vlsv.hw06.MainActivity
import net.ukr.vlsv.hw06.R
import net.ukr.vlsv.hw06.data.DailyWeatherList
import java.text.SimpleDateFormat
import java.util.*
import kotlin.contracts.Returns
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import net.ukr.vlsv.hw06.WeatherActivity

class WeatherAdapter (var mDataset: ArrayList<DailyWeatherList>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    companion object {
        lateinit var dailyWeather: DailyWeatherList
    }
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mTextViewDate: TextView
        var mTextViewTemp: TextView
        var mTextViewWind: TextView

        init {
            mTextViewDate = v.findViewById(R.id.TextView_Date) as TextView
            mTextViewTemp = v.findViewById(R.id.TextView_Temp) as TextView
            mTextViewWind = v.findViewById(R.id.TextView_Wind) as TextView
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemWeather = mDataset[position]

        val weatherDate = Date(itemWeather.dt.times(1000))
        val weatherTemp = getTemp(itemWeather.main.temp)
        val weatherWind = itemWeather.wind.speed.toInt()

        val df = SimpleDateFormat("dd.MM.yyyy hh:mm")

        holder.mTextViewDate.setText("Date: ${df.format(weatherDate)}")
        holder.mTextViewTemp.setText("Temp: $weatherTemp °${MainActivity.TEMP_UNIT}")
        holder.mTextViewWind.setText("Wind: $weatherWind")
    }

    override fun getItemCount(): Int = mDataset.size

    fun getTemp(temp: Double): String {
        var mTemp: Double = temp

        if (MainActivity.TEMP_UNIT == "C") {mTemp = temp - 273.15}

        return mTemp.toInt().toString()
    }
}