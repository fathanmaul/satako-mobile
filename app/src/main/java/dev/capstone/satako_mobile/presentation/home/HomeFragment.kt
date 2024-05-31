package dev.capstone.satako_mobile.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            diagnoseButton.setOnClickListener {
                view.findNavController().navigate(R.id.action_home_fragment_to_diagnoseFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        )
    }
}