package com.BELAIBET.simulation.front

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.BELAIBET.simulation.R

class SlotsIconsAdapter(private val slotsIconList: List<Int>) :
    RecyclerView.Adapter<SlotsIconsAdapter.IconViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val itemPick =
            LayoutInflater.from(parent.context).inflate(R.layout.slot_icon, parent, false)
        return IconViewHolder(itemPick)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val slot = slotsIconList[position]
        holder.bind(slot)
    }

    override fun getItemCount(): Int {
        return slotsIconList.size
    }

    inner class IconViewHolder(iconView: View) : RecyclerView.ViewHolder(iconView) {
        private val slotView: ImageView = iconView.findViewById(R.id.slot_icon)

        fun bind(slot: Int) {
            slotView.setImageResource(slot)
        }
    }
}