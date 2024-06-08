package dev.capstone.satako_mobile.presentation.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentSettingsBinding
import dev.capstone.satako_mobile.utils.sumCacheSize

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showSizeCache()
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
            btnClearCache.setOnClickListener {

            }
        }
    }

    private fun showSizeCache() {
        var cacheSize = sumCacheSize(requireContext())
        cacheSize /= 1024
        if (cacheSize != 0L) {
            binding.cacheSize.text = cacheSize.toString()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}