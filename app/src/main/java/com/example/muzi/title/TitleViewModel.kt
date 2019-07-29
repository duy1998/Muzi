package com.example.muzi.title

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzi.data.Resource
import com.example.muzi.data.TitleItem
import com.example.muzi.search.Repository
import com.example.muzi.utils.isNetworkConnected

class TitleViewModel : ViewModel(){
    val repository: Repository = Repository.getInstance()

    var resourceData = MutableLiveData<Resource<List<TitleItem>>>()

    fun init(){
        resourceData = repository.getListTitle()
    }

    fun getDataTitle(){
        if (isNetworkConnected()) {
            resourceData.value = Resource.load()
            repository.getTitleData()
        } else {
            resourceData.value = Resource.error()
        }
    }


}