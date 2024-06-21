package dev.capstone.satako_mobile.presentation.articles.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.model.dummy.Article
import dev.capstone.satako_mobile.databinding.FragmentDetailArticleBinding

class DetailArticleFragment : Fragment() {

    private val binding: FragmentDetailArticleBinding by lazy {
        FragmentDetailArticleBinding.inflate(layoutInflater)
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

        val article = DetailArticleFragmentArgs.fromBundle(arguments as Bundle).articleItem
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        showArticleContent(article)
    }

    private fun showArticleContent(article: Article) {
        with(binding) {
            articleTitle.text = article.title
            articleAuthor.text = article.author
            articleImage.setImageResource(article.imageResourceId)
            articleDescription.text = article.description
        }
    }
}