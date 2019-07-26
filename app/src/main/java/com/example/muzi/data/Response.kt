package com.example.muzi.data

import com.google.gson.annotations.SerializedName

data class Response<T> (
    @SerializedName("kind") val kind: String?,
    @SerializedName("etag") val tag: String?,
    @SerializedName("nextPageToken") val nextPageToken: String?,
    @SerializedName("prevPageToken") val prevPageToken: String?,
    @SerializedName("pageInfo") val pageInfo: PageInfo?,
    @SerializedName("items") val items:  List<T>?
)