package dev.capstone.satako_mobile.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R

class SplashFragment : Fragment() {

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
            // If user is logged in, navigate to HomeFragment
            // view.findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

            // If user is not logged in, navigate to OnboardingFragment
            view.findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
        }, 3000)
    }
}