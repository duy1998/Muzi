package com.example.muzi.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzi.data.SearchItem
import com.example.muzi.utils.isNetworkConnected
import com.example.muzi.widget.Paging

class SearchViewModel : ViewModel(){

    var searchRepository: SearchRepository = SearchRepository()

    var searchList = MutableLiveData<List<SearchItem>>()

    val paging = Paging()

    fun init(){
        searchList = searchRepository.getListSearch()
    }

    fun getDataSearch(s:String){
        if (isNetworkConnected()) {
            paging.isLoading = true
            searchRepository.getSearchData(s,paging)
        } else {

        }
    }
    fun resetPaging(){
        paging.nextPageToken = null
        paging.isLoading = false
    }

}