package dev.capstone.satako_mobile.presentation.home.diagnose.result

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.BulletSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentResultBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.presentation.home.diagnose.DiagnoseViewModel
import dev.capstone.satako_mobile.utils.DateFormatter.formatIsoDate
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
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateResult()
        val imageUri = ResultFragmentArgs.fromBundle(arguments as Bundle).imageUri
        val predictResult = ResultFragmentArgs.fromBundle(arguments as Bundle).predictResult
        val history = ResultFragmentArgs.fromBundle(arguments as Bundle).history
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }

            if (history != null) {
                diseaseNameTextView.text = history.disease
                dateTextView.text = history.createdAt?.let { formatIsoDate(it) }
                descriptionText.text = history.description
                causesText.text = history.causes
                val solutionText = history.solutions?.let { setSolutionText(it) }
                binding.solutionText.text = Html.fromHtml(solutionText, Html.FROM_HTML_MODE_COMPACT)

                Glide.with(requireContext())
                    .load(history.imageUrl)
                    .error(R.drawable.sample_scan)
                    .into(resultImageView)
            } else {
                if (predictResult != null) {
                    diseaseNameTextView.text = predictResult.disease
                    dateTextView.text = date
                    descriptionText.text = predictResult.description
                    causesText.text = predictResult.causes
                    val solutionText = predictResult.solutions?.let { setSolutionText(it) }
                    binding.solutionText.text = Html.fromHtml(solutionText, Html.FROM_HTML_MODE_COMPACT)
                }
                diagnoseViewModel.setImageUri(
                    imageUri?.let { Uri.parse(it) }
                )
                diagnoseViewModel.imageUri.observe(viewLifecycleOwner) {
                    showPreview(it)
                }
            }

        }
    }

    private fun setSolutionText(text: String): String {
        val solution = text.trimIndent()
        val htmlText = solution.split("\n").joinToString("<br>") { "<li>&nbsp;&nbsp;&nbsp;&nbsp;$it</li>" }
        val finalHtmlText = "<ul>$htmlText</ul>"
        return finalHtmlText
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