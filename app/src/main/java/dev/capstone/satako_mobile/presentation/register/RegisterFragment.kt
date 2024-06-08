package dev.capstone.satako_mobile.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentRegisterBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.utils.showBottomSheetDialog


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

            registerButton.setOnClickListener {
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
                                    showBottomSheetDialog(
                                        requireContext(),
                                        getString(R.string.register_error),
                                        R.drawable.error_image
                                    )
                                }

                                is Result.Loading -> {
                                    showLoading(true)
                                }

                                is Result.Success -> {
                                    showLoading(false)
                                    showBottomSheetDialog(
                                        requireContext(),
                                        getString(R.string.success_register),
                                        R.drawable.success_image,
                                        onClick = {
                                            view.findNavController().popBackStack()
                                        }
                                    )
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
//            registerButton?.setOnClickListener {
//                Jika Berhasil
//                showBottomSheetDialog(
//                    requireContext(),
//                    getString(R.string.success_register),
//                    R.drawable.success_image,
//                   onClick = {
//                        view.findNavController().popBackStack()
//                    }
//              )
//                Jika Gagal
//                showBottomSheetDialog(
//                    requireContext(),
//                    getString(R.string.please_fill_out_all_forms),
//                    R.drawable.success_image
//                )
//            }
//        }
//    }

//}
