package com.BELAIBET.simulation.front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.BELAIBET.simulation.databinding.SlotsResultLayoutBinding

class SlotsResultFragment: Fragment() {

    private var _binding: SlotsResultLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =  SlotsResultLayoutBinding.inflate(inflater, container, false)
        binding.scorePanelBelaibet.text= (arguments?.getInt("amount_belaibet")?.times(7)).toString()
        return binding.root
    }

    override fun onDestroyView() {
        try {
            super.onDestroyView()
            _binding = null
        } catch (e:Exception) {

        }
    }
}