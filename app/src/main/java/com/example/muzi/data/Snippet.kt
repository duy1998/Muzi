package com.example.muzi.data

import com.example.demoyoutubeapi.data.Thumbnails
import com.google.gson.annotations.SerializedName

data class Snippet (
    @SerializedName("publishedAt") val publishedAt: String?,
    @SerializedName("channelId") val channelId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("channelTitle") val channelTitle:String?,
    @SerializedName("thumbnails") val thumbnails: Thumbnails?
)