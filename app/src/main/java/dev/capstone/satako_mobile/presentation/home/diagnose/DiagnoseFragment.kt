package dev.capstone.satako_mobile.presentation.home.diagnose

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentDiagnoseBinding
import dev.capstone.satako_mobile.utils.gone
import dev.capstone.satako_mobile.utils.show

class DiagnoseFragment : Fragment() {

    private val binding: FragmentDiagnoseBinding by lazy {
        FragmentDiagnoseBinding.inflate(layoutInflater)
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
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        showPreview(null)
    }


    private fun showPreview(uri: Uri?) {
        if (uri != null) {
            binding.previewImageView.setImageURI(uri)
            binding.previewImageView.show()
            binding.previewTextView.show()
        } else {
            binding.previewImageView.gone()
            binding.previewTextView.gone()
        }
    }


}