package com.nandaiqbalh.newsapp.presentation.ui.home.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.databinding.FragmentArticleBinding
import com.nandaiqbalh.newsapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

	private var _binding: FragmentArticleBinding? = null
	private val binding get () = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentArticleBinding.inflate(layoutInflater, container, false)

		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}