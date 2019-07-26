package com.example.muzi.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.muzi.Retrofit
import com.example.muzi.UserServices
import com.example.muzi.data.*
import com.example.muzi.utils.isNetworkDisConnected
import com.example.muzi.widget.Paging
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class SearchRepository {
    private var compositeDisposable = CompositeDisposable()

    private val service = Retrofit.instance.create(UserServices::class.java)

    private val listSearch = MutableLiveData<List<SearchItem>>()

//    fun getSearchData( s: String, paging: Paging){
//        service.searchByKeyWord(
//            Constant.API_KEY,
//            "snippet",
//            Constant.MUSIC,
//            s,
//            paging.nextPageToken,
//            11,
//           "video"
//        )
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object :Observer<Response<SearchResource>>{
//                lateinit var disposable: Disposable
//                override fun onComplete() {
//                    disposable.dispose()
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                    disposable = d
//                    compositeDisposable.add(disposable)
//                }
//
//                override fun onNext(t: Response<SearchResource>) {
//                    paging.nextPageToken = t.nextPageToken
//                    var listVideoID = ""
//                    for (i in t.items!!){
//                        listVideoID += i.id.videoId+ ","
//                    }
//                    if (listVideoID.isNotEmpty()) {
//                        service.getInfoVideos(Constant.API_KEY,
//                            "statistics,snippet,contentDetails",
//                            listVideoID
//                        )
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(object :Observer<Response<VideoResource>>{
//                                lateinit var disposable: Disposable
//                                override fun onComplete() {
//                                    paging.isLoading = false
//                                    disposable.dispose()
//                                }
//
//                                override fun onSubscribe(d: Disposable) {
//                                    disposable = d
//                                    compositeDisposable.add(disposable)
//                                }
//
//                                override fun onNext(t1: Response<VideoResource>) {
//                                    var listItem : MutableList<SearchItem> = ArrayList()
//                                    for (i in t1.items!!){
//                                        listItem.add(SearchItem(i.id,
//                                            i.contentDetails.duration!!,
//                                            i.snippet.title!!,
//                                            i.snippet.channelTitle!!,
//                                            i.statistics.viewCount!!,
//                                            i.snippet.thumbnails!!.default!!.url))
//                                    }
//                                    listSearch.value = listItem
//                                }
//
//                                override fun onError(e: Throwable) {
//                                    Log.d("Duy",e.toString())
//                                    if (e.isNetworkDisConnected()){
//
//                                    }
//                                }
//
//                            })
//
//                    }
//
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.d("Duy",e.toString()+"sadasd")
//                    if (e.isNetworkDisConnected()){
//
//                    }
//                }
//
//            })
//    }

    fun getSearchData(s: String, paging: Paging) {
        service.searchByKeyWord(Constant.API_KEY, "snippet", Constant.MUSIC, s, paging.nextPageToken, 11, "video")
            .subscribeOn(Schedulers.io())
            .flatMap { t ->
                paging.nextPageToken = t.nextPageToken
                var listVideoID = ""
                for (i in t.items!!) {
                    listVideoID += i.id.videoId + ","
                }
                service.getInfoVideos(
                    Constant.API_KEY,
                    "statistics,snippet,contentDetails",
                    listVideoID
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<VideoResource>> {
                lateinit var disposable: Disposable
                override fun onComplete() {
                    paging.isLoading = false
                    disposable.dispose()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                    compositeDisposable.add(disposable)
                }

                override fun onNext(t1: Response<VideoResource>) {
                    val listItem: MutableList<SearchItem> = ArrayList()
                    for (i in t1.items!!) {
                        listItem.add(
                            SearchItem(
                                i.id,
                                i.contentDetails.duration!!,
                                i.snippet.title!!,
                                i.snippet.channelTitle!!,
                                i.statistics.viewCount!!,
                                i.snippet.thumbnails!!.default!!.url
                            )
                        )
                    }
                    listSearch.value = listItem
                }

                override fun onError(e: Throwable) {
                    Log.d("Duy", e.toString())
                    if (e.isNetworkDisConnected()) {

                    }
                }

            })
    }

    fun getListSearch(): MutableLiveData<List<SearchItem>> {
        return listSearch
    }
}
