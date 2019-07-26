package com.example.muzi

import com.example.muzi.data.Response
import com.example.muzi.data.SearchResource
import com.example.muzi.data.VideoResource
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServices {
    @GET("youtube/v3/search")
    fun searchByKeyWord(
        @Query("key") apiKey: String,
        @Query("part") part: String,
        @Query("videoCategoryId") videoCategoryId:String,
        @Query("q") q: String,
        @Query("pageToken") pageToken:String?,
        @Query("maxResults") maxResults: Int,
        @Query("type") type:String
    ): Observable<Response<SearchResource>>

    @GET("youtube/v3/videos")
    fun getInfoVideos(
        @Query("key") apiKey: String,
        @Query("part") part:String,
        @Query("id") id:String
    ):Observable<Response<VideoResource>>


}