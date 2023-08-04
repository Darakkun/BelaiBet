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

    private lateinit var adapter: SlotsAdapter
    private lateinit var layoutManager: LinearLayoutManager
    var slotsList = listOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SlotsLayoutBinding.inflate(inflater, container, false)


        layoutManager = GridLayoutManager(this.requireContext(), 3)
        binding.recSlots.layoutManager = layoutManager

        // Создание списка слотов
        slotsList = createSlotsList()
        adapter = SlotsAdapter(slotsList)
        binding.recSlots.adapter = adapter

        binding.spinButton.setOnClickListener {
            numberOfShufles++
            if (numberOfShufles == maxNumberOfShufles) {
                val position = 50
                binding.recSlots.smoothScrollToPosition(position)
                slotsList = createFakeList()
                adapter = SlotsAdapter(slotsList)
                binding.recSlots.adapter = adapter
                Handler(Looper.getMainLooper()).postDelayed({
                    val bundle = Bundle()
                    bundle.putInt("amount", currentBet*500)
                    findNavController().navigate(R.id.Slots_result_fragment, bundle)
                }, 2000)

                } else {
                val position = 50
//                modelProvider.currentScore += position
                slotsList = createSlotsList()
                adapter = SlotsAdapter(slotsList)
                binding.recSlots.adapter = adapter
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


    private fun createSlotsList(): List<Int> {
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
        shuffleList(iconsList)
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


    fun <T> shuffleList(list: MutableList<T>) {
        val random = java.util.Random()
        for (i in list.size - 1 downTo 1) {
            val j = random.nextInt(i + 1)
            val temp = list[i]
            list[i] = list[j]
            list[j] = temp
        }
    }

    class SlotsAdapter(private val slotsList: List<Int>) :
        RecyclerView.Adapter<SlotsAdapter.SlotViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.slot_icon, parent, false)
            return SlotViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
            val slot = slotsList[position]
            holder.bind(slot)
        }

        override fun getItemCount(): Int {
            return slotsList.size
        }

        inner class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val slotTextView: ImageView = itemView.findViewById(R.id.slot_icon)

            fun bind(slot: Int) {
                slotTextView.setImageResource(slot)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}