package com.BELAIBET.simulation.front

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.BELAIBET.simulation.R

class SlotsIconsAdapterBelai(private val intListBelai: List<Int>) :
    RecyclerView.Adapter<SlotsIconsAdapterBelai.IconViewHolder>() {

    override fun onCreateViewHolder(parentBelai: ViewGroup, viewTypeBelai: Int): IconViewHolder {
        return try {
            val slotPick =
                LayoutInflater.from(parentBelai.context).inflate(R.layout.slot_icon, parentBelai, false)
            IconViewHolder(slotPick)
        }catch (e:Exception){
            val slotPick =
                LayoutInflater.from(parentBelai.context).inflate(R.layout.slot_icon, parentBelai, false)
            IconViewHolder(slotPick)
        }

    }

    override fun onBindViewHolder(viewHolderBelai: IconViewHolder, posBelai: Int) {
        try {
            viewHolderBelai.doNothing()
            val icon = intListBelai[posBelai]
            viewHolderBelai.connect(icon)
        }catch (e:Exception){
            val icon = intListBelai[posBelai]
            viewHolderBelai.connect(icon)
        }
    }

    override fun getItemCount(): Int {
        return try {
            intListBelai.size
        } catch (e:Exception){
            0
        }

    }

    inner class IconViewHolder(iconViewBelai: View) : RecyclerView.ViewHolder(iconViewBelai) {
        private val slotView: ImageView = iconViewBelai.findViewById(R.id.slot_icon)

        fun doNothing(){
            try {
                val a = 150
            }catch (e:Exception)
            {}
        }

        fun connect(iconSlot: Int) {
            try {
                slotView.setImageResource(iconSlot)
            } catch (e:Exception){

            }

        }
    }
}