package com.dicoding.picodiploma.myviewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = WeatherAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnCity.setOnClickListener {
            showLoading(true)

            val city = editCity.text.toString()
            val apiKey = "93a3696714297ee5a9f65486aa8cb824"
            val url = "https://api.openweathermap.org/data/2.5/group?id=$city&units=metric&appid=$apiKey"

            val client = AsyncHttpClient()
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
                    showLoading(false)
                    try {
                        val listItems = ArrayList<WeatherItems>()

                        val result = String(responseBody)
                        val responseObject = JSONObject(result)
                        val list = responseObject.getJSONArray("list")

                        for (i in 0 until list.length()) {
                            val weather = list.getJSONObject(i)
                            val weatherItems = WeatherItems()
                            weatherItems.id = weather.getInt("id")
                            weatherItems.name = weather.getString("name")
                            weatherItems.currentWeather = weather.getJSONArray("weather").getJSONObject(0).getString("main")
                            weatherItems.description = weather.getJSONArray("weather").getJSONObject(0).getString("description")
                            val tempInKelvin = weather.getJSONObject("main").getDouble("temp")
                            val tempInCelsius = tempInKelvin - 273
                            weatherItems.temperature = DecimalFormat("##.##").format(tempInCelsius)
                            listItems.add(weatherItems)
                        }

                        adapter.listData = listItems
                    } catch (e: Exception) {
                        Log.d("Exception", e.message.toString())
                    }
                }

                override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                    showLoading(false)
                    Log.d("onFailure", error.message.toString())
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

}
