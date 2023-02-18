package com.tzuhsien.noodoeassignment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tzuhsien.noodoeassignment.NavGraphDirections
import com.tzuhsien.noodoeassignment.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.editUserName.doAfterTextChanged {
            viewModel.userName.value = it.toString()
        }

        binding.editPassword.doAfterTextChanged {
            viewModel.password.value = it.toString()
        }

        binding.btnLogIn.setOnClickListener {
            viewModel.checkToLogIn()
        }

        viewModel.error.observe(viewLifecycleOwner){
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.navigateToParkingInfo.observe(viewLifecycleOwner){
            if(it) {
                findNavController().navigate(NavGraphDirections.actionGlobalParkingInfoFragment())
                viewModel.doneNavigation()
            }
        }

        return binding.root
    }

}