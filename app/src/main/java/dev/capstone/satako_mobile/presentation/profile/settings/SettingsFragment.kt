package dev.capstone.satako_mobile.presentation.profile.settings

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.children
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentSettingsBinding
import dev.capstone.satako_mobile.utils.sumCacheSize

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var selectedTheme: Int = 0


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
            btnThemes.setOnClickListener {
                showChangeThemeDialog()
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

    private fun showChangeThemeDialog() {
        val options = arrayOf(
            "Light",
            "Dark",
            "System default"
        )
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Choose theme")
            .setSingleChoiceItems(options, -1) { dialog, which ->
                val theme = when (which) {
                    0 -> "Light"
                    1 -> "Dark"
                    else -> "System default"
                }
                selectedTheme = which

            }
            .setPositiveButton("Apply") { dialog, which ->
                Toast.makeText(requireContext(), "$selectedTheme Selected", Toast.LENGTH_SHORT)
                    .show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }.show()
    }

    private fun changeTheme() {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}