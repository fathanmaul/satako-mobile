package dev.capstone.satako_mobile.presentation.home.diagnose.result

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.databinding.FragmentResultBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.presentation.home.diagnose.DiagnoseViewModel
import dev.capstone.satako_mobile.utils.date

class ResultFragment : Fragment() {

    private val binding: FragmentResultBinding by lazy {
        FragmentResultBinding.inflate(layoutInflater)
    }

    private val diagnoseViewModel: DiagnoseViewModel by viewModels {
        ViewModelFactory(requireContext())
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

        animateResult()
        val imageUri = ResultFragmentArgs.fromBundle(arguments as Bundle).imageUri
        val predictResult = ResultFragmentArgs.fromBundle(arguments as Bundle).predictResult
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }

            diseaseNameTextView.text = predictResult.disease
            dateTextView.text = date
            descriptionText.text = predictResult.description
            causesText.text = predictResult.causes
            solutionText.text = predictResult.solutions
        }
        diagnoseViewModel.setImageUri(
            imageUri?.let { Uri.parse(it) }
        )
        diagnoseViewModel.imageUri.observe(viewLifecycleOwner) {
            showPreview(it)
        }
    }

    private fun animateResult() {
        val diseaseTextView =
            ObjectAnimator.ofFloat(binding.diseaseNameTextView, View.ALPHA, 1f).setDuration(700)
        val dateTextContainer =
            ObjectAnimator.ofFloat(binding.dateTextContainer, View.ALPHA, 1f).setDuration(500)
        val descriptionContainer =
            ObjectAnimator.ofFloat(binding.descriptionContainer, View.ALPHA, 1f).setDuration(500)
        val causesContainer =
            ObjectAnimator.ofFloat(binding.causesContainer, View.ALPHA, 1f).setDuration(500)
        val solutionContainer =
            ObjectAnimator.ofFloat(binding.solutionContainer, View.ALPHA, 1f).setDuration(500)
        AnimatorSet().apply {
            playSequentially(
                diseaseTextView,
                dateTextContainer,
                descriptionContainer,
                causesContainer,
                solutionContainer
            )
            start()
        }
    }

    private fun showPreview(imageUri: Uri?) {
        binding.resultImageView.setImageURI(imageUri)
    }

}