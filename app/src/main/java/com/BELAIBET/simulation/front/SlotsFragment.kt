package com.BELAIBET.simulation.front

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.BELAIBET.simulation.R
import com.BELAIBET.simulation.databinding.SlotsLayoutBinding
import java.util.Random


class SlotsFragment : Fragment() {

    private var _binding: SlotsLayoutBinding? = null
    private val binding get() = _binding!!
    private var maxNumberOfShufles: Int = Random().nextInt(5) + 6
    private var numberOfShufles: Int = 0
    private var currentBet = 1

    private lateinit var slotsIconsAdapter: SlotsIconsAdapter
    private lateinit var gridManger: LinearLayoutManager
    var iconSlotsList = listOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SlotsLayoutBinding.inflate(inflater, container, false)
        gridManger = GridLayoutManager(this.requireContext(), 3)
        binding.recSlots.layoutManager = gridManger
        iconSlotsList = buildSlotsList()
        slotsIconsAdapter = SlotsIconsAdapter(iconSlotsList)
        binding.recSlots.adapter = slotsIconsAdapter

        binding.spinButton.setOnClickListener {
            numberOfShufles++
            if (numberOfShufles == maxNumberOfShufles) {
                val position = 50
                binding.recSlots.smoothScrollToPosition(position)
                iconSlotsList = createFakeList()
                slotsIconsAdapter = SlotsIconsAdapter(iconSlotsList)
                binding.recSlots.adapter = slotsIconsAdapter
                Handler(Looper.getMainLooper()).postDelayed({
                    val bundle = Bundle()
                    bundle.putInt("amount", currentBet*500)
                    findNavController().navigate(R.id.Slots_result_fragment, bundle)
                }, 2000)

                } else {
                val position = 50
//                modelProvider.currentScore += position
                iconSlotsList = buildSlotsList()
                slotsIconsAdapter = SlotsIconsAdapter(iconSlotsList)
                binding.recSlots.adapter = slotsIconsAdapter
                binding.recSlots.smoothScrollToPosition(position)
            }
        }

        binding.minusBet.setOnClickListener {
            if (currentBet > 0) currentBet--
            binding.currentBet.text = currentBet.toString()
            binding.scorePanel.text = (currentBet*500).toString()
        }

        binding.plusBet.setOnClickListener {
            if (currentBet < 10) currentBet++
            binding.currentBet.text = currentBet.toString()
            binding.scorePanel.text = (currentBet*500).toString()
        }


        return binding.root
    }


    private fun buildSlotsList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        randomaziList(iconsList)
        return iconsList
    }

    private fun createFakeList(): List<Int> {
        val iconsList = mutableListOf<Int>()
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.seven)
        iconsList.add(R.drawable.melon)
        iconsList.add(R.drawable.gold)
        iconsList.add(R.drawable.grape)
        iconsList.add(R.drawable.lemon)
        iconsList.add(R.drawable.diamond)
        iconsList.add(R.drawable.cherry)
        iconsList.add(R.drawable.orange)
        iconsList.add(R.drawable.star)
        return iconsList
    }


    private fun <T> randomaziList(list: MutableList<T>) {
        val random = java.util.Random()
        for (i in list.size - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}