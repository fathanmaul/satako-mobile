package dev.capstone.satako_mobile.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.presentation.ViewModelFactory

class PlaceholderFragment : Fragment() {

    private val splashViewModel: SplashViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_placeholder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashViewModel.getSession().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                // Navigate to the next Fragment
                view.findNavController().navigate(R.id.action_placeholderFragment2_to_home_fragment)
            } else {
                // Navigate to the next Fragment
                view.findNavController()
                    .navigate(R.id.action_placeholderFragment2_to_onboardingFragment)
            }
        }
    }
}