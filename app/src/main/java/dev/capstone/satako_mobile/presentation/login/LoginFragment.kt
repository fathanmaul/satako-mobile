package dev.capstone.satako_mobile.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.BottomSheetBinding
import dev.capstone.satako_mobile.databinding.FragmentLoginBinding
import dev.capstone.satako_mobile.viewmodel.ViewModelFactory
import dev.capstone.satako_mobile.viewmodel.login.LoginViewModel
import dev.capstone.satako_mobile.databinding.FragmentOnboardingBinding
import dev.capstone.satako_mobile.utils.showBottomSheetDialog

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
                showBottomSheetDialog(
                    requireContext(),
                    getString(R.string.login_failed),
                    R.drawable.error_image,
                    buttonColorResId = R.color.danger,
                    onClick = {
                        toHome(view)
                    }
                )
            }
        }
    }

    private fun toHome(view: View) {
        view.findNavController().navigate(
            R.id.action_loginFragment_to_homeFragment, null,
            NavOptions.Builder().setPopUpTo(R.id.main_navigation, true).build()
        )
    }
}