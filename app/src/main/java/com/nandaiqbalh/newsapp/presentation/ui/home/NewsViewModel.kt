package com.nandaiqbalh.newsapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.newsapp.data.network.models.news.Article
import com.nandaiqbalh.newsapp.data.network.models.news.NewsResponse
import com.nandaiqbalh.newsapp.data.repository.NewsRepository
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
	private val newsRepository: NewsRepository
) : ViewModel() {

	val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
	var breakingNewsPage = 1

	init {
		getBreakingNews("id")
	}

	fun getBreakingNews(countryCode: String) = viewModelScope.launch {
		breakingNews.postValue(Resource.Loading())
		val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
		breakingNews.postValue(handleBreakingNewsResponse(response))
	}


	private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
		if(response.isSuccessful) {
			response.body()?.let { resultResponse ->
				return Resource.Success(resultResponse)
			}
		}
		return Resource.Error(response.message())
	}

}