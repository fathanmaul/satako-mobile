package dev.capstone.satako_mobile.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.presentation.ViewModelFactory

class SplashFragment : Fragment() {

    private val splashViewModel: SplashViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.getSession().observe(viewLifecycleOwner) { session ->
                auth = Firebase.auth
                val currentUser = auth.currentUser
                if (session.isNotEmpty() && currentUser != null) {
                    view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    view.findNavController()
                        .navigate(R.id.action_splashFragment_to_onboardingFragment)
                }
            }
        }, 3000)
    }
}