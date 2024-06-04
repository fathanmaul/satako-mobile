package dev.capstone.satako_mobile.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentRegisterBinding
import dev.capstone.satako_mobile.viewmodel.ViewModelFactory
import dev.capstone.satako_mobile.viewmodel.register.RegisterViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }

            signInButton.setOnClickListener {
                val name = nameEditText.text.toString().trim()
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val confirmPassword = confirmPasswordEditText.text.toString().trim()

                registerViewModel.register(name, email, password, confirmPassword)
                    .observe(viewLifecycleOwner) {
                        if (it != null) {
                            when (it) {
                                is Result.Error -> {
                                    showLoading(false)
                                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT)
                                        .show()
                                }

                                is Result.Loading -> {
                                    showLoading(true)
                                }

                                is Result.Success -> {
                                    showLoading(false)
                                    Toast.makeText(
                                        requireContext(),
                                        it.data.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    view.findNavController().popBackStack()
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        with(binding) {
            if (state) {
                pbRegister.visibility = View.VISIBLE
            } else {
                pbRegister.visibility = View.GONE
            }
        }
    }

}
