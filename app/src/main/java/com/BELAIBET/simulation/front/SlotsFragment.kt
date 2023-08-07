package com.BELAIBET.simulation.front

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.BELAIBET.simulation.R
import com.BELAIBET.simulation.databinding.SlotsLayoutBinding
import java.util.Random


class SlotsFragment : Fragment() {

    private var _bindingBelai: SlotsLayoutBinding? = null
    private val bindingBelai get() = _bindingBelai!!
    private var maxNumberOfShufles: Int = Random().nextInt(5) + 6
    private var numberOfShufles: Int = 0
    private var currentBetBelai = 1

    private lateinit var slotsIconsAdapterBelai: SlotsIconsAdapterBelai
    private lateinit var gridMangerBelai: LinearLayoutManager
    var iconSlotsListBelai = listOf<Int>()

    override fun onCreateView(
        inflaterBelai: LayoutInflater, containerBelai: ViewGroup?,
        savedInstanceStateBelai: Bundle?
    ): View {

        _bindingBelai = SlotsLayoutBinding.inflate(inflaterBelai, containerBelai, false)
        gridMangerBelai = GridLayoutManager(this.requireContext(), 3)
        bindingBelai.recyclerForSlotsBelaibet.layoutManager = gridMangerBelai
        iconSlotsListBelai = buildSlotsList()
        slotsIconsAdapterBelai = SlotsIconsAdapterBelai(iconSlotsListBelai)
        bindingBelai.recyclerForSlotsBelaibet.adapter = slotsIconsAdapterBelai

        bindingBelai.spinButtonBelaibet.setOnClickListener {
            numberOfShufles++
            if (numberOfShufles == maxNumberOfShufles) {
                val position = 50
                bindingBelai.recyclerForSlotsBelaibet.smoothScrollToPosition(position)
                iconSlotsListBelai = createFakeList()
                slotsIconsAdapterBelai = SlotsIconsAdapterBelai(iconSlotsListBelai)
                bindingBelai.recyclerForSlotsBelaibet.adapter = slotsIconsAdapterBelai
                bindingBelai.winBracketBelaibet.visibility=View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    val bundle = Bundle()
                    bundle.putInt("amount_belaibet", currentBetBelai*500)
                    findNavController().navigate(R.id.Slots_result_fragment, bundle)
                }, 2000)

                } else {
                val position = 50
//                modelProvider.currentScore += position
                iconSlotsListBelai = buildSlotsList()
                slotsIconsAdapterBelai = SlotsIconsAdapterBelai(iconSlotsListBelai)
                bindingBelai.recyclerForSlotsBelaibet.adapter = slotsIconsAdapterBelai
                bindingBelai.recyclerForSlotsBelaibet.smoothScrollToPosition(position)
            }
        }

        bindingBelai.minusBet.setOnClickListener {
            if (currentBetBelai > 0) currentBetBelai--
            bindingBelai.currentBet.text = currentBetBelai.toString()
            bindingBelai.scorePanelBelaibet.text = (currentBetBelai*500).toString()
        }

        bindingBelai.plusBetBelaibet.setOnClickListener {
            if (currentBetBelai < 10) currentBetBelai++
            bindingBelai.currentBet.text = currentBetBelai.toString()
            bindingBelai.scorePanelBelaibet.text = (currentBetBelai*500).toString()
        }


        return bindingBelai.root
    }


    private fun buildSlotsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        randomaziList(iconsList)
        return iconsList
    }

    private fun createFakeList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.sevenbelai)
        iconsList.add(R.drawable.melonbelai)
        iconsList.add(R.drawable.gold_belai)
        iconsList.add(R.drawable.grape_belai)
        iconsList.add(R.drawable.lemon_belai)
        iconsList.add(R.drawable.diamond_belai)
        iconsList.add(R.drawable.cherrybelai)
        iconsList.add(R.drawable.orangebelai)
        iconsList.add(R.drawable.starbelai)
        return iconsList
    }


    private fun <T> randomaziList(listBelai: MutableList<T>) {
        val randomBelai = java.util.Random()
        for (i in listBelai.size - 1 downTo 1) {
            val j = randomBelai.nextInt(i + 1)
            val tempBelai = listBelai[i]
            listBelai[i] = listBelai[j]
            listBelai[j] = tempBelai
        }
    }



    override fun onDestroyView() {
        try {
            super.onDestroyView()
            _bindingBelai = null
        } catch (e:Exception) {

        }
    }

}