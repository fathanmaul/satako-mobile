package dev.capstone.satako_mobile.presentation.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentLoginBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.presentation.register.RegisterViewModel
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

                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()

                loginViewModel.login(email, password).observe(viewLifecycleOwner) {
                    if (it != null) {
                        when (it) {
                            is Result.Error -> {
                                showLoading(false)
                                showBottomSheetDialog(
                                    requireContext(),
                                    getString(R.string.login_failed),
                                    R.drawable.error_image,
                                    buttonColorResId = R.color.danger,
                                    onClick = {}
                                )
                            }
                            Result.Loading -> showLoading(true)
                            is Result.Success -> {
                                showLoading(false)
                                val token = it.data.loginResult.token
                                loginViewModel.saveTokenSession(token) {
                                    toHome(view)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun toHome(view: View) {
        view.findNavController().navigate(
            R.id.action_loginFragment_to_homeFragment, null,
            NavOptions.Builder().setPopUpTo(R.id.main_navigation, true).build()
        )
    }
}