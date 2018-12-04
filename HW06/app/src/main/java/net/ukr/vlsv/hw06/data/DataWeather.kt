package net.ukr.vlsv.hw06.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DailyWeather(
    @SerializedName("cod")     val cod: String,
    @SerializedName("message") val message: String,
    @SerializedName("cnt")     val cnt: String,
    @SerializedName("list")    val infoDailyWeatherList: List<DailyWeatherList>
)

data class DailyWeatherList(
    @SerializedName("dt")       val dt: Long,
    @SerializedName("main")     val main: Main,
    @SerializedName("temp")     val temp: Temp,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("weather")  val weather: List<Weather>,
    @SerializedName("wind")    val wind: Wind
//    @SerializedName("clouds")   val clouds: Double
//    @SerializedName("rain")     val rain: Double
)

data class Main(
    @SerializedName("temp")       val temp: Double,
    @SerializedName("pressure")   val pressure: Double,
    @SerializedName("humidity")   val humidity: Int,
    @SerializedName("temp_min")   val temp_min: Double,
    @SerializedName("temp_max")   val temp_max: Double,
    @SerializedName("sea_level")  val sea_level: Double,
    @SerializedName("temp_kf")    val temp_kf: Double,
    @SerializedName("grnd_level") val ground_level: Double
)

data class Weather(
    @SerializedName("id")          val weather_condition_id: Int,
    @SerializedName("main")        val weather_main: String,
    @SerializedName("description") val weather_description: String,
    @SerializedName("icon")        val weather_icon: String
)

data class Temp(
    @SerializedName("day")   val day: Double,
    @SerializedName("min")   val min: Double,
    @SerializedName("max")   val max: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve")   val eve: Double,
    @SerializedName("morn")  val morn: Double
)

data class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg")   val deg: Double
)