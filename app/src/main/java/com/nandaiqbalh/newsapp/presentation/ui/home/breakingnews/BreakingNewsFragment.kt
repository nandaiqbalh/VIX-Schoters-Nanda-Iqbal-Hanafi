package com.nandaiqbalh.newsapp.presentation.ui.home.breakingnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.databinding.FragmentBreakingNewsBinding
import com.nandaiqbalh.newsapp.other.util.Constants.Companion.QUERY_PAGE_SIZE
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsAdapter
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

	private var _binding: FragmentBreakingNewsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: NewsViewModel by activityViewModels()

	private lateinit var newsAdapter: NewsAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeData()
		setupRecyclerView()

		newsAdapter.setOnItemClickListener {
			val bundle = Bundle().apply {
				putSerializable("article", it)
			}
			findNavController().navigate(
				R.id.action_breakingNewsFragment_to_articleFragment,
				bundle
			)
		}
	}

	private fun observeData() {

		viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
			when(response) {
				is Resource.Success -> {
					binding.tvBreakingEmpty.visibility = View.GONE

					hideProgressBar()
					response.data?.let { newsResponse ->
						newsAdapter.differ.submitList(newsResponse.articles.toList())
						val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
						isLastPage = viewModel.breakingNewsPage == totalPages
						if(isLastPage) {
							binding.rvBreakingNews.setPadding(0, 0, 0, 0)
						}
					}
				}
				is Resource.Error -> {
					hideProgressBar()
					binding.tvBreakingEmpty.visibility = View.VISIBLE
				}
				is Resource.Loading -> {
					showProgressBar()
				}
			}
		})
	}

	private fun hideProgressBar() {
		binding.paginationProgressBar.visibility = View.INVISIBLE
		isLoading = false
	}

	private fun showProgressBar() {
		binding.paginationProgressBar.visibility = View.VISIBLE
		isLoading = true
	}

	var isLoading = false
	var isLastPage = false
	var isScrolling = false

	private val scrollListener = object : RecyclerView.OnScrollListener() {
		override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
			super.onScrolled(recyclerView, dx, dy)

			val layoutManager = recyclerView.layoutManager as LinearLayoutManager
			val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
			val visibleItemCount = layoutManager.childCount
			val totalItemCount = layoutManager.itemCount

			val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
			val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
			val isNotAtBeginning = firstVisibleItemPosition >= 0
			val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
			val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
					isTotalMoreThanVisible && isScrolling
			if(shouldPaginate) {
				viewModel.getBreakingNews("id")
				isScrolling = false
			}
		}

		override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
			super.onScrollStateChanged(recyclerView, newState)
			if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
				isScrolling = true
			}
		}
	}

	private fun setupRecyclerView() {
		newsAdapter = NewsAdapter()
		binding.rvBreakingNews.apply {
			adapter = newsAdapter
			layoutManager = LinearLayoutManager(activity)
			addOnScrollListener(this@BreakingNewsFragment.scrollListener)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}