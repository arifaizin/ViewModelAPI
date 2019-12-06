package com.dicoding.picodiploma.myviewmodel

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.weather_items.view.*

/**
 * Created by Emeth on 10/31/2016.
 */

class WeatherAdapter internal constructor(private val context: Context) : RecyclerView.Adapter<WeatherAdapter.ListViewHolder>() {
    internal var listData = arrayListOf<WeatherItems>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        //untuk menghubungkan dengan layout item
        val itemView = LayoutInflater.from(context).inflate(R.layout.weather_items, parent, false)
        val viewHolder = ListViewHolder(itemView)
        return viewHolder
    }

    override fun getItemCount(): Int {
        //untuk jumlah item yang ditampilkan
        return listData.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        //memberi data ke ViewHolder sesuai posisinya
        val hero = listData[position]
        holder.bind(hero)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //mencocokkan data dengan komponen
        fun bind(weatherItems: WeatherItems) {
            with(itemView){
                textKota.text = weatherItems.name
                textTemp.text = weatherItems.temperature
                textDesc.text = weatherItems.description
            }
        }
    }
}





