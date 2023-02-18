package com.tzuhsien.noodoeassignment.timezone

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzuhsien.noodoeassignment.R

class TimeZoneFragment : Fragment() {

    companion object {
        fun newInstance() = TimeZoneFragment()
    }

    private lateinit var viewModel: TimeZoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_time_zone, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TimeZoneViewModel::class.java)
        // TODO: Use the ViewModel
    }

}