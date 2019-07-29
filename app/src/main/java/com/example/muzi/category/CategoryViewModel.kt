package com.example.muzi.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muzi.data.CategoryItem
import com.example.muzi.data.Resource
import com.example.muzi.search.Repository
import com.example.muzi.utils.isNetworkConnected

class CategoryViewModel : ViewModel(){
    val repository: Repository = Repository.getInstance()

    var resourceData = MutableLiveData<Resource<List<CategoryItem>>>()

    fun init(){
        resourceData = repository.getListCategory()
    }

    fun getDataCategory(){
        if (isNetworkConnected()) {
            resourceData.value = Resource.load()
            repository.getCategoryData()
        } else {
            resourceData.value = Resource.error()
        }
    }


}