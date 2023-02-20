package com.tzuhsien.noodoeassignment.timezone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        checkLoginStatus()
        setUpTimeZoneSpinner()


        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.doneTimeZoneUpdate.observe(viewLifecycleOwner){
            if (it) {
                Toast.makeText(context, getString(R.string.update_success), Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun checkLoginStatus(){
        // Navigate to login if no logged in user found
        viewModel.isLoggedIn.observe(viewLifecycleOwner) {
            if (it) {
                binding.txtUserNameEmail.text = viewModel.user?.userName
            } else {
                findNavController().navigate(NavGraphDirections.actionGlobalLoginFragment())
            }
        }
    }

    private fun setUpTimeZoneSpinner(){
        val adapter = ArrayAdapter(requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, viewModel.timeZones)

        binding.spinnerTimeZone.adapter = adapter

        //set the default selected value as the original time zone setting
        val spinnerPosition: Int = adapter.getPosition(viewModel.originalSelectedTimeZone)
        binding.spinnerTimeZone.setSelection(spinnerPosition)

        binding.spinnerTimeZone.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                viewModel.updateUserTimeZone(binding.spinnerTimeZone.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}