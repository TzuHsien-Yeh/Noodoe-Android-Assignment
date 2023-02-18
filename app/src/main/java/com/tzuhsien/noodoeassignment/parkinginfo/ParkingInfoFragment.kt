package com.tzuhsien.noodoeassignment.parkinginfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tzuhsien.noodoeassignment.databinding.FragmentParkingInfoBinding

class ParkingInfoFragment : Fragment() {

    private lateinit var binding: FragmentParkingInfoBinding

    private val viewModel:ParkingInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentParkingInfoBinding.inflate(layoutInflater)
        return binding.root
    }

}