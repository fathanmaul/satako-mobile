package dev.capstone.satako_mobile.presentation.home

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
import dev.capstone.satako_mobile.databinding.FragmentHomeBinding
import dev.capstone.satako_mobile.presentation.articles.ArticlesAdapter
import dev.capstone.satako_mobile.presentation.articles.OnArticleClickListener

class HomeFragment : Fragment(), OnArticleClickListener {

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
            txtSeeAll.setOnClickListener {
                view.findNavController().navigate(R.id.action_home_fragment_to_articleFragment)
            }
        }
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        val articles = generateDummyArticles()
        val adapter = ArticlesAdapter(articles, this)

        with(binding) {
            rvArticles.layoutManager = LinearLayoutManager(requireContext())
            rvArticles.adapter = adapter
        }

    }

    private fun generateDummyArticles(): List<Article> {
        val dummyList = mutableListOf<Article>()
        for (i in 0..4) {
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
        val action = HomeFragmentDirections.actionHomeFragmentToDetailArticleFragment(article)
        findNavController().navigate(action)
    }
}