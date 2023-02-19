package com.tzuhsien.noodoeassignment.timezone

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tzuhsien.noodoeassignment.NavGraphDirections
import com.tzuhsien.noodoeassignment.R
import com.tzuhsien.noodoeassignment.databinding.FragmentTimeZoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimeZoneFragment : Fragment() {

    private lateinit var binding: FragmentTimeZoneBinding
    private val viewModel: TimeZoneViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTimeZoneBinding.inflate(layoutInflater)

        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                binding.txtUserNameEmail.text = viewModel.user?.userName
            } else {
                findNavController().navigate(NavGraphDirections.actionGlobalLoginFragment())
            }
        }


        return binding.root
    }

}