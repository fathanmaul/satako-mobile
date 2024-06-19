package dev.capstone.satako_mobile.presentation.login

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentLoginBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.utils.TextListener
import dev.capstone.satako_mobile.utils.gone
import dev.capstone.satako_mobile.utils.isEmailValid
import dev.capstone.satako_mobile.utils.show
import dev.capstone.satako_mobile.utils.showBottomSheetDialog
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var auth: FirebaseAuth
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
            registerTextView.setOnClickListener {
                view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            emailEditText.addTextChangedListener(object : TextListener {
                override fun onTextListener(s: CharSequence?) {
                    if (!s.isNullOrEmpty()) {
                        if (!s.toString().isEmailValid()) {
                            binding.tvEmailError?.text = getString(R.string.invalid_email)
                            binding.tvEmailError?.show()
                        } else {
                            binding.tvEmailError?.gone()
                        }
                    } else {
                        binding.tvEmailError?.gone()
                    }

                }
            })
            passwordEditText.addTextChangedListener(object : TextListener {
                override fun onTextListener(s: CharSequence?) {
                    if (!s.isNullOrEmpty()) {
                        if (s.toString().isEmpty()) {
                            binding.tvPasswordError?.text = getString(R.string.password_empty)
                            binding.tvPasswordError?.show()
                        } else {
                            binding.tvEmailError?.gone()
                        }
                    } else {
                        binding.tvPasswordError?.gone()
                    }
                }
            })

//            googleSignInButton.setOnClickListener {
//                signInWithGoogle()
//            }

            signInButton.setOnClickListener {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()

                if (!validateLogin(email, password)) return@setOnClickListener

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
                                val token = it.data.dataLogin?.token
                                val username = it.data.dataLogin?.customUser?.username
                                val emailUser = it.data.dataLogin?.customUser?.email

                                if (token != null && username != null && emailUser != null) {
                                    loginViewModel.saveTokenSession(token, username, email) {
                                        toHome(view)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun signInWithGoogle() {
        showLoading(true)
        val credentialManager =
            CredentialManager.create(requireContext())
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(getString(R.string.your_web_client_id))
            .build()
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        lifecycleScope.launch {
            try {
                val result: GetCredentialResponse = credentialManager.getCredential(
                    request = request,
                    context = requireActivity(),
                )
                handleSignIn(result)
            } catch (e: GetCredentialException) {
                showLoading(false)
                errorBottomSheet(getString(R.string.failed_to_get_credential))
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        showLoading(false)
        when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        errorBottomSheet(getString(R.string.invalid_googleid))
                    }
                } else {
                    errorBottomSheet(getString(R.string.unexpected_type_of_credential))
                }
            }

            else -> {
                errorBottomSheet(getString(R.string.unexpected_type_of_credential))
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    errorBottomSheet(getString(R.string.failed_login))
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val token = currentUser.getIdToken(true).toString()
            val username = currentUser.displayName
            val email = currentUser.email

            if (username != null && email != null) {
                loginViewModel.saveTokenSession(token, username, email) {
                    toHome(requireView())
                }
            }
        } else {
            errorBottomSheet(getString(R.string.login_failed))
        }
    }

    private fun showLoading(isLoading: Boolean) {
        showLoadingDialog(isLoading)
    }

    private fun toHome(view: View) {
        view.findNavController().navigate(
            R.id.action_loginFragment_to_homeFragment, null,
            NavOptions.Builder().setPopUpTo(R.id.main_navigation, true).build()
        )
    }

    private fun errorBottomSheet(msg: String) {
        showBottomSheetDialog(
            requireContext(),
            msg,
            R.drawable.error_image,
            buttonColorResId = R.color.danger,
            onClick = {}
        )
    }

    override fun onPause() {
        super.onPause()
        binding.passwordEditText.text?.clear()
    }

    private fun validateLogin(
        email: String,
        password: String
    ): Boolean {
        if (email.isEmpty()) {
            binding.tvEmailError?.text = getString(R.string.email_empty)
            binding.tvEmailError?.show()
            return false
        }

        if (!email.isEmailValid()) {
            binding.tvEmailError?.text = getString(R.string.invalid_email)
            binding.tvEmailError?.show()
            return false
        }

        if (password.isEmpty()) {
            binding.tvPasswordError?.text = getString(R.string.password_empty)
            binding.tvPasswordError?.show()
            return false
        }
        return true
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