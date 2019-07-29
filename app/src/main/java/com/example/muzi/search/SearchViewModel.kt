package com.example.muzi.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzi.data.Resource
import com.example.muzi.data.SearchItem
import com.example.muzi.utils.isNetworkConnected
import com.example.muzi.widget.Paging

class SearchViewModel : ViewModel(){

    val repository: Repository = Repository.getInstance()

    var resourceSearchData = MutableLiveData<Resource<List<SearchItem>>>()

    val paging = Paging()

    fun init(){
        resourceSearchData = repository.getListSearch()
    }

    fun getDataSearch(s:String){
        if (isNetworkConnected()) {
            paging.isLoading = true
            resourceSearchData.value = Resource.load()
            repository.getSearchData(s,paging)
        } else {
            resourceSearchData.value = Resource.error()
        }
    }
    fun resetPaging(){
        paging.nextPageToken = null
        paging.isLoading = false
    }

}