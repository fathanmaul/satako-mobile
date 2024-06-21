package dev.capstone.satako_mobile.presentation.register

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentRegisterBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.utils.TextListener
import dev.capstone.satako_mobile.utils.ValidationAuth
import dev.capstone.satako_mobile.utils.gone
import dev.capstone.satako_mobile.utils.isEmailValid
import dev.capstone.satako_mobile.utils.show
import dev.capstone.satako_mobile.utils.showBottomSheetDialog


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels {
        ViewModelFactory(requireContext())
    }
    private var loadingDialog: AlertDialog? = null

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

            nameEditText.addTextChangedListener(object : TextListener {
                override fun onTextListener(s: CharSequence?) {
                    ValidationAuth.isFieldEmpty(
                        s.toString().trim(),
                        tvNameError,
                        requireContext().getString(R.string.name_empty)
                    )

                    ValidationAuth.validateName(
                        requireContext(),
                        s.toString().trim(),
                        tvNameError
                    )
                }
            })

            emailEditText.addTextChangedListener(object : TextListener {
                override fun onTextListener(s: CharSequence?) {
                    ValidationAuth.isFieldEmpty(
                        s.toString().trim(),
                        tvEmailError,
                        requireContext().getString(R.string.email_empty)
                    )

                    ValidationAuth.validateEmail(
                        requireContext(),
                        s.toString().trim(),
                        tvEmailError
                    )
                }
            })

            passwordEditText.addTextChangedListener(object : TextListener {
                override fun onTextListener(s: CharSequence?) {
                    ValidationAuth.validatePassword(
                        requireContext(),
                        s.toString().trim(),
                        tvPasswordError
                    )
                }
            })

            confirmPasswordEditText.addTextChangedListener(object : TextListener {
                override fun onTextListener(s: CharSequence?) {
                    ValidationAuth.validatePasswordMatch(
                        requireContext(),
                        passwordEditText.text.toString().trim(),
                        s.toString().trim(),
                        tvConfirmPasswordError
                    )
                }
            })

            registerButton.setOnClickListener {
                val name = nameEditText.text.toString().trim()
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                val confirmPassword = confirmPasswordEditText.text.toString().trim()

                if (!validateRegister(
                        name,
                        email,
                        password,
                        confirmPassword
                    )
                ) return@setOnClickListener
                registerViewModel.register(name, email, password, confirmPassword)
                    .observe(viewLifecycleOwner) {
                        if (it != null) {
                            when (it) {
                                is Result.Error -> {
                                    showLoading(false)
                                    showBottomSheetDialog(
                                        requireContext(),
                                        getString(R.string.register_error),
                                        R.drawable.error_image,
                                        buttonColorResId = R.color.danger,
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
//                pbRegister.visibility = View.VISIBLE
                registerButton.isEnabled = false
                backButton.isEnabled = false
                showLoadingDialog(true)
            } else {
//                pbRegister.visibility = View.GONE
                registerButton.isEnabled = true
                backButton.isEnabled = true
                showLoadingDialog(false)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun validateRegister(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        if (ValidationAuth.isFieldEmpty(
                name,
                binding.tvNameError,
                requireContext().getString(R.string.name_empty)
            )
        ) isValid = false
        if (!ValidationAuth.validateName(requireContext(), name, binding.tvNameError)) isValid =
            false
        if (!ValidationAuth.validateEmail(requireContext(), email, binding.tvEmailError)) isValid =
            false
        if (ValidationAuth.isFieldEmpty(
                password,
                binding.tvPasswordError,
                requireContext().getString(R.string.required_password)
            )
        ) isValid = false
        if (!ValidationAuth.validatePasswordMatch(
                requireContext(),
                password,
                confirmPassword,
                binding.tvConfirmPasswordError
            )
        ) isValid = false
        if (!ValidationAuth.validatePassword(requireContext(), password, binding.tvPasswordError))
            isValid = false

        return isValid
    }

    private fun showLoadingDialog(
        state: Boolean
    ) {
        if (state) {
            if (loadingDialog == null) {
                loadingDialog = MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Please wait...")
                    .setCancelable(false)
                    .create()
            }
            loadingDialog?.show()
        } else {
            loadingDialog?.dismiss()
            loadingDialog = null
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
