package com.example.muzi.data

import com.google.gson.annotations.SerializedName

data class SearchResource (
    @SerializedName("kind") val kind :String,
    @SerializedName("etag") val etag:String,
    @SerializedName("id") val id:ID,
    @SerializedName("snippet") val snippet: Snippet
    )