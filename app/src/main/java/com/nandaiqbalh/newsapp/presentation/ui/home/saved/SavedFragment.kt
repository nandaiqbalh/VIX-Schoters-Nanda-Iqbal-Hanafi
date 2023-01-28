package com.nandaiqbalh.newsapp.presentation.ui.home.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.databinding.FragmentSavedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

	private var _binding: FragmentSavedBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment

		_binding = FragmentSavedBinding.inflate(layoutInflater, container, false)

		return binding.root

	}

}