package com.udacity.asteroidradar.adapters

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.ItemMainBinding

import com.udacity.asteroidradar.room.entity.Asteroid

class MainAdapter(var data: List<Asteroid>) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {
    val TAG = "MainAdapter"

    var onItemClick: ((Asteroid) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
        val viewBinding = ItemMainBinding.inflate(view, viewGroup, false)

        return MyViewHolder(viewBinding)
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.bind(data[i])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MyViewHolder(var viewBinding: ItemMainBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(response: Asteroid) {

            viewBinding.apply {

                tvDate.text = response.closeApproachDate
                tvTitle.text = response.name

                rlContainer.setOnClickListener {
                    onItemClick?.invoke(response)
                }
                if (response.hazardous == true) img.setImageResource(R.drawable.ic_status_potentially_hazardous)
                else img.setImageResource(R.drawable.ic_status_normal)

                img.contentDescription = response.name
            }

        }

    }
}

