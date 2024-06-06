package dev.capstone.satako_mobile.presentation.home.diagnose

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private val binding: FragmentResultBinding by lazy {
        FragmentResultBinding.inflate(layoutInflater)
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

        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }
    }

    private fun animateResult() {
        val diseaseTextView =
            ObjectAnimator.ofFloat(binding.diseaseNameTextView, View.ALPHA, 1f).setDuration(700)
        val dateTextContainer =
            ObjectAnimator.ofFloat(binding.dateTextContainer, View.ALPHA, 1f).setDuration(500)
        val descriptionContainer =
            ObjectAnimator.ofFloat(binding.descriptionContainer, View.ALPHA, 1f).setDuration(500)
        val solutionContainer =
            ObjectAnimator.ofFloat(binding.solutionContainer, View.ALPHA, 1f).setDuration(500)
        AnimatorSet().apply {
            playSequentially(
                diseaseTextView,
                dateTextContainer,
                descriptionContainer,
                solutionContainer
            )
            start()
        }
    }

}