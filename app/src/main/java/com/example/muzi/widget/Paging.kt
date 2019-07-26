package com.example.muzi.widget

class Paging{
    var nextPageToken:String? =null

    var isLoading:Boolean = false

    fun hasNext():Boolean{
        return !nextPageToken.isNullOrEmpty()
    }

}