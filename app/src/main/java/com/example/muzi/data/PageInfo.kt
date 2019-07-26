package com.example.muzi.data

import com.google.gson.annotations.SerializedName

class PageInfo (
    @SerializedName("totalResults") val totalResults:Int?,
    @SerializedName("resultsPerPage") val resultsPerPage:Int?
)