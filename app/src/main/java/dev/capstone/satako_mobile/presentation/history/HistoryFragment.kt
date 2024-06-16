package dev.capstone.satako_mobile.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.response.HistoryItem
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentHistoryBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.utils.showBottomSheetDialog

class HistoryFragment : Fragment() {

    private val binding: FragmentHistoryBinding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private val historyViewModel: HistoryViewModel by viewModels {
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
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        getHistory()
    }

    private fun getHistory() {
        historyViewModel.getHistory().observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Error -> {
                        showLoading(false)
                        showBottomSheetDialog(
                            requireContext(),
                            getString(R.string.failed_to_get_history),
                            R.drawable.error_image,
                            buttonColorResId = R.color.danger,
                            onClick = {}
                        )
                    }

                    Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        setHistory(it.data.historyItem)
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            pbHistory.visibility = if (isLoading) View.VISIBLE else View.GONE
            historyRecyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
        }
    }

    private fun setHistory(history: List<HistoryItem?>?) {
        val adapter = HistoryAdapter()
        adapter.submitList(history)

        with(binding) {
            historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            historyRecyclerView.adapter = adapter
        }
    }

}