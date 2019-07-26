package com.example.muzi.data

import com.google.gson.annotations.SerializedName

data class Statistics constructor(
    @SerializedName("viewCount") val viewCount : Long?,
    @SerializedName("likeCount") val likeCount: Long?,
    @SerializedName("dislikeCount") val dislikeCount: Long?,
    @SerializedName("favoriteCount") val favoriteCount: Long?,
    @SerializedName("commentCount") val commentCount: Long?
)