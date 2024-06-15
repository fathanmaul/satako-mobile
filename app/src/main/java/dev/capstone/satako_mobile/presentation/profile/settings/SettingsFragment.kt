package dev.capstone.satako_mobile.presentation.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.pref.ThemePreference
import dev.capstone.satako_mobile.data.pref.dataStoreTheme
import dev.capstone.satako_mobile.databinding.FragmentSettingsBinding
import dev.capstone.satako_mobile.utils.sumCacheSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory(ThemePreference.getInstance(requireContext().dataStoreTheme))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        CoroutineScope(Dispatchers.Main).launch {
            val options = arrayOf(
                getString(R.string.light),
                getString(R.string.dark),
                getString(R.string.system_default)
            )
            val dialog = MaterialAlertDialogBuilder(requireContext())
            val themeSettings = withContext(Dispatchers.IO){
                viewModel.getThemeSettings()
            }
            var selectedItem = when (themeSettings) {
                0 -> 0
                1 -> 1
                else -> 2
            }
            dialog.setTitle(getString(R.string.choose_theme))
                .setSingleChoiceItems(
                    options,
                    selectedItem
                ) { dialog, which ->
                    selectedItem = which
                }
                .setPositiveButton("Apply") { dialog, which ->
                    when (selectedItem) {
                        0 -> viewModel.saveTheme(0)
                        1 -> viewModel.saveTheme(1)
                        2 -> viewModel.saveTheme(null)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
            dialog.show()
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class SettingsViewModelFactory(private val themePreference: ThemePreference) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(themePreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}