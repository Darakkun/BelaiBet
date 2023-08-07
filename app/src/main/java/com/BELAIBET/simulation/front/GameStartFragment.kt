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

    private var _bindingBelai: GameStartLayoutBinding? = null
    private val bindingBelai get() = _bindingBelai!!

    override fun onCreateView(
        inflaterBelai: LayoutInflater, containerBelai: ViewGroup?,
        savedInstanceStateBelai: Bundle?
    ): View {

        _bindingBelai =  GameStartLayoutBinding.inflate(inflaterBelai, containerBelai, false)
        bindingBelai.startGame.setOnClickListener {
            findNavController().navigate(R.id.action_GameStartFragment_to_SlotsFragment)
        }
        return bindingBelai.root
    }

    override fun onDestroyView() {
        try {
            super.onDestroyView()
            _bindingBelai = null
        } catch (extraBelai:Exception) {

        }
    }

}