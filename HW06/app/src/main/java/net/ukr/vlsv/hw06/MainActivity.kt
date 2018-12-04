package net.ukr.vlsv.hw06

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import net.ukr.vlsv.hw06.Clases.InternetInfo
import net.ukr.vlsv.hw06.adapters.WeatherAdapter
import net.ukr.vlsv.hw06.data.DailyWeather
import net.ukr.vlsv.hw06.data.DailyWeatherList
import net.ukr.vlsv.hw06.listeners.RecyclerTouchListener
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var adapterWeather: WeatherAdapter

    companion object {
        val DETAIL_KEY: String = "C"
        var TEMP_UNIT: String = "C"
        var TEMP_UNIT_KEY: String = "TEMP_UNIT"
    }
    
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(TEMP_UNIT_KEY, TEMP_UNIT)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            val tempUnit = savedInstanceState.getString(TEMP_UNIT_KEY)
            if (tempUnit != "") TEMP_UNIT = tempUnit
        }

        RecyclerView_Weather.addOnItemTouchListener(RecyclerTouchListener(this, RecyclerView_Weather, object : RecyclerTouchListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                val weathetIntent = Intent(applicationContext, WeatherActivity::class.java)
                val adapterItem = adapterWeather.mDataset[position]
                weathetIntent.putExtra(DETAIL_KEY + "_dt", adapterItem.dt)
                weathetIntent.putExtra(DETAIL_KEY + "_temp", adapterItem.main.temp)
                weathetIntent.putExtra(DETAIL_KEY + "_wind", adapterItem.wind.speed)
                weathetIntent.putExtra(DETAIL_KEY + "_pres", adapterItem.main.pressure)
                weathetIntent.putExtra(DETAIL_KEY + "_weath", adapterItem.weather[0].weather_description)
                startActivity(weathetIntent)
            }
            override fun onItemLongClick(view: View?, position: Int) {
            }
        }))
    }

    override fun onStart() {
        super.onStart()

        val internetInfo: InternetInfo = InternetInfo()
        if (internetInfo.getInternetStatus() == false) {
            val noInternetIntent = Intent(this, NoInternetActivity::class.java)
            startActivityForResult(noInternetIntent, 1)
            return
        }

        RecyclerView_Weather.setHasFixedSize(true)                               // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        RecyclerView_Weather.layoutManager = LinearLayoutManager(this)           // используем linear layout manager

        makeNetworkRequestAsyncTask().execute()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            1-> if (resultCode !== Activity.RESULT_OK) {finish()}
            2-> {
                if (resultCode == Activity.RESULT_OK) {
                    TEMP_UNIT = data!!.extras.getString(resources.getText(R.string.KEY_Settings_TempUnit).toString())
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.MenuItem_Settings -> {
                val settingsIntent = Intent(this, SettingsActivity::class.java)
                settingsIntent.putExtra(resources.getText(R.string.KEY_Settings_TempUnit).toString(), TEMP_UNIT)
                startActivityForResult(settingsIntent, 2)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class makeNetworkRequestAsyncTask: AsyncTask<String, String, String>() {
        override fun doInBackground(vararg p0: String?): String {
            val client = OkHttpClient.Builder().build()
            val API_URL = "http://api.openweathermap.org/data/2.5/forecast?q=Cherkasy&mode=json&APPID=f791b93359beda848eebd31c8a909c45"
            val request = Request.Builder()
                .url(API_URL)
                .build()
            val response = client.newCall(request).execute()
            val responseRezult =  response.body()!!.string()

            return responseRezult.toString()
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar_Weather.visibility = View.VISIBLE
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            progressBar_Weather.visibility = View.INVISIBLE

            val weatherArray = ArrayList<DailyWeatherList>()

            if (result !== "") {
                val gsonWeather = Gson().fromJson(result, DailyWeather::class.java)

                for (itemWeather in gsonWeather.infoDailyWeatherList) {
                    weatherArray.add(itemWeather)
                }
            }

            adapterWeather = WeatherAdapter(weatherArray)
            RecyclerView_Weather.adapter = adapterWeather                 // создаем адаптер
//            RecyclerView_Weather.adapter.setD
        }
    }
}
