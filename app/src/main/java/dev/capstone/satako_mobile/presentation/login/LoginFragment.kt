package dev.capstone.satako_mobile.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentLoginBinding
import dev.capstone.satako_mobile.databinding.FragmentOnboardingBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


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
                    NavOptions.Builder().setPopUpTo(R.id.loginFragment, true).build()
                )
                Toast.makeText(context, "Sign in successful", Toast.LENGTH_SHORT).show()
            }
        }
    }

}