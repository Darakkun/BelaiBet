package com.BELAIBET.simulation.front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.BELAIBET.simulation.R
import com.BELAIBET.simulation.databinding.GameStartLayoutBinding

class GameStartFragment:Fragment() {

    private var _binding: GameStartLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =  GameStartLayoutBinding.inflate(inflater, container, false)
        binding.startGame.setOnClickListener {
            findNavController().navigate(R.id.action_GameStartFragment_to_SlotsFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}