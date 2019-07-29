package com.example.muzi.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.muzi.Retrofit
import com.example.muzi.UserServices
import com.example.muzi.data.*
import com.example.muzi.utils.isNetworkDisConnected
import com.example.muzi.widget.Paging
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class Repository private constructor(){
    private object Holder { val INSTANCE = Repository() }

    private var compositeDisposable = CompositeDisposable()

    private val service = Retrofit.instance.create(UserServices::class.java)

    private val listSearchData = MutableLiveData<Resource<List<SearchItem>>>()

    private val listTitleData = MutableLiveData<Resource<List<TitleItem>>>()

    private val listCategoryData = MutableLiveData<Resource<List<CategoryItem>>>()

    companion object {
        @JvmStatic
        fun getInstance(): Repository{
            return Holder.INSTANCE
        }

    }
    fun getSearchData(s: String, paging: Paging) {
        service.searchByKeyWord(Constant.API_KEY, "snippet", Constant.MUSIC, s, paging.nextPageToken, 11, "video")
            .subscribeOn(Schedulers.io())
            .flatMap { t ->
                paging.nextPageToken = t.nextPageToken
                Log.d("TOKEN", paging.nextPageToken!!)
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
                        try {
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
                        catch (e:Exception){

                        }

                    }
                    listSearchData.value = Resource.success(listItem)
                }

                override fun onError(e: Throwable) {
                    Log.d("Duy", e.toString())
                    if (e.isNetworkDisConnected()) {
                        listSearchData.value = Resource.error()
                    }
                }

            })
    }

    fun getListSearch(): MutableLiveData<Resource<List<SearchItem>>> {
        return listSearchData
    }

    fun getTitleData(){
        val tmp  = ArrayList<TitleItem>()
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(TitleItem("Music","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))



        listTitleData.value = Resource.success(tmp)
    }

    fun getCategoryData(){
        val tmp  = ArrayList<CategoryItem>()
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))
        tmp.add(CategoryItem("Sweet But Psycho","Ava Max","https://i.ytimg.com/vi/nkhpGC10OVw/hqdefault.jpg"))



        listCategoryData.value = Resource.success(tmp)
    }

    fun getListTitle():MutableLiveData<Resource<List<TitleItem>>>{
        return listTitleData
    }

    fun getListCategory():MutableLiveData<Resource<List<CategoryItem>>>{
        return listCategoryData
    }
}


