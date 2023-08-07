package com.BELAIBET.simulation.front

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.BELAIBET.simulation.databinding.SlotsResultLayoutBinding

class SlotsResultFragment: Fragment() {

    private var _bindingBelai: SlotsResultLayoutBinding? = null
    private val bindingBelai get() = _bindingBelai!!

    override fun onCreateView(
        inflaterBelaiBet: LayoutInflater, containerBelai: ViewGroup?,
        savedInstanceStateBelai: Bundle?
    ): View {
        _bindingBelai =  SlotsResultLayoutBinding.inflate(inflaterBelaiBet, containerBelai, false)
        bindingBelai.scorePanelBelaibet.text= (arguments?.getInt("amount_belaibet")?.times(7)).toString()
        return bindingBelai.root
    }

    override fun onDestroyView() {
        try {
            super.onDestroyView()
            _bindingBelai = null
        } catch (extra:Exception) {

        }
    }
}