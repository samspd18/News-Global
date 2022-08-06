package com.satya.newsglobal.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.satya.newsglobal.ui.models.NewsServiceModel
import com.satya.newsglobal.ui.network.RetroInstance
import com.satya.newsglobal.ui.network.RetroService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel(application: Application) : AndroidViewModel(application){

    private var newsListLiveData: MutableLiveData<NewsServiceModel> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    private var  selectedChip = ""
    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        throwable.printStackTrace()
    }

    fun getNewsListServiceObserver(): MutableLiveData<NewsServiceModel> {
        return newsListLiveData
    }
    private fun apiCallFinished() {
        isLoading.postValue(true)
    }
    private fun duringTheApiCall() {
        isLoading.postValue(false)
    }

    fun sendSelectedChipData(selectedChipText: String) {
//        selectedChipTextForApiCall = selectedChipText
        makeApiCallForListOfData(selectedChipText)
        duringTheApiCall()
    }

    fun makeApiCallForListOfData(selectedChipTextForApiCall: String) {

        selectedChip = if(selectedChipTextForApiCall == "" || selectedChip == selectedChipTextForApiCall) {
            "all"
        } else {
            selectedChipTextForApiCall
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            val newsListResponse = retroInstance.getNews(selectedChip)
            newsListLiveData.postValue(newsListResponse)
            apiCallFinished()
        }
    }

    fun makeApiCallForCategoryWiseNews(selectedCategory: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
            val response = retroInstance.getNews(selectedCategory)
            newsListLiveData.postValue(response)
            apiCallFinished()
        }
    }
}