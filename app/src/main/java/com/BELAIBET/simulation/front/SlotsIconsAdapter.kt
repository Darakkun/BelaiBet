package com.BELAIBET.simulation.front

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.BELAIBET.simulation.R

class SlotsIconsAdapter(private val list: List<Int>) :
    RecyclerView.Adapter<SlotsIconsAdapter.IconViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        return try {
            val slotPick =
                LayoutInflater.from(parent.context).inflate(R.layout.slot_icon, parent, false)
            IconViewHolder(slotPick)
        }catch (e:Exception){
            val slotPick =
                LayoutInflater.from(parent.context).inflate(R.layout.slot_icon, parent, false)
            IconViewHolder(slotPick)
        }

    }

    override fun onBindViewHolder(viewHolder: IconViewHolder, pos: Int) {
        try {
            val icon = list[pos]
            viewHolder.connect(icon)
        }catch (e:Exception){
            val icon = list[pos]
            viewHolder.connect(icon)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class IconViewHolder(iconView: View) : RecyclerView.ViewHolder(iconView) {
        private val slotView: ImageView = iconView.findViewById(R.id.slot_icon)

        fun connect(iconSlot: Int) {
            slotView.setImageResource(iconSlot)
        }
    }
}