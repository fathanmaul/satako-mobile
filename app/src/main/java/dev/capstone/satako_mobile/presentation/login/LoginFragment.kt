package dev.capstone.satako_mobile.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentLoginBinding
import dev.capstone.satako_mobile.viewmodel.ViewModelFactory
import dev.capstone.satako_mobile.viewmodel.login.LoginViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
            registerTextView.setOnClickListener {
                view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            signInButton.setOnClickListener {
                view.findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment, null,
                    androidx.navigation.navOptions {
                        popUpTo(R.id.main_navigation) {
                            inclusive = true
                        }
                    }
                )
                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
            }
        }
    }

}