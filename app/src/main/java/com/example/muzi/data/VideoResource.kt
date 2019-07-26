package com.example.muzi.data

import com.google.gson.annotations.SerializedName

data class VideoResource(
    @SerializedName("kind") val kind :String,
    @SerializedName("etag") val etag:String,
    @SerializedName("id") val id:String,
    @SerializedName("statistics") val statistics: Statistics,
    @SerializedName("contentDetails") val contentDetails: ContentDetails,
    @SerializedName("snippet") val snippet: Snippet
)