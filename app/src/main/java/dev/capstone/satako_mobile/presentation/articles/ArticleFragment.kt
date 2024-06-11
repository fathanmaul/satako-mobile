package dev.capstone.satako_mobile.presentation.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.model.dummy.Article
import dev.capstone.satako_mobile.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(), OnArticleClickListener {

    private val binding: FragmentArticleBinding by lazy {
        FragmentArticleBinding.inflate(layoutInflater)
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

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val articles = generateDummyArticles()
        val adapter = ArticlesAdapter(articles, this)

        with(binding) {
            articlesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            articlesRecyclerView.adapter = adapter
        }

    }

    private fun generateDummyArticles(): List<Article> {
        val dummyList = mutableListOf<Article>()
        for (i in 0..15) {
            dummyList.add(
                Article(
                    "Title $i",
                    "Author $i",
                    "https://picsum.photos/200/300",
                    description = getString(R.string.example_lorem)
                )
            )
        }
        return dummyList
    }

    override fun onArticleClick(article: Article) {
        val action = ArticleFragmentDirections.actionArticleFragmentToDetailArticleFragment(article)
        findNavController().navigate(action)
    }

}