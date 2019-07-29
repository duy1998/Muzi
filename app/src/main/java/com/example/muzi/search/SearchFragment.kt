package com.example.muzi.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muzi.MainActivity
import com.example.muzi.R
import com.example.muzi.data.Resource
import com.example.muzi.data.SearchItem
import com.example.muzi.utils.Queue
import com.example.muzi.widget.EndlessRecyclerViewListener
import com.example.muzi.widget.LoadMoreI
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_toolbar_search.*


class SearchFragment : Fragment() {

    private val keywordAdapter = KeywordAdapter()

    private val searchAdapter = SearchAdapter()

    private var repoKeyword =Queue(activity)

    private var currKeywordSearch: String = ""

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchListListener: EndlessRecyclerViewListener

    private var dispose: Disposable? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repoKeyword.read()

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        viewModel.init()

        initRecyclerView()

        initListener()

    }
    fun filterKeyword(currKeyWord:String, result:String) : Boolean{
            if(result.length>= currKeyWord.length){
                if (result.substring(0,currKeyWord.length).compareTo(currKeyWord)==0) {
                    return true
                }
            }
        return false
    }

    private fun initRecyclerView(){
        rvKeyword.layoutManager = LinearLayoutManager(this.context)
        rvKeyword.adapter = keywordAdapter

        rvSearch.layoutManager = LinearLayoutManager(this.context)
        rvSearch.adapter = searchAdapter

        viewModel.resourceSearchData.observe(this,
            Observer<Resource<List<SearchItem>>> { t ->
                when(t.status){
                    Resource.LOADING ->{

                    }
                    Resource.SUCCESSED ->{
                        if(searchAdapter.itemCount == 0) {
                            searchAdapter.setData(t.data)
                            keywordAdapter.setData(null)
                            Log.d("Loadmore","First Data")
                        } else{
                            searchAdapter.addMoreData(t.data)
                            Log.d("Loadmore","Next Data")
                        }
                    }
                    Resource.NETWORK_ERROR ->{
                        Toast.makeText(this.context,"Wifi",Toast.LENGTH_LONG).show()
                    }
                }

            })
    }

    private fun initListener(){
        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    repoKeyword.enQueue(etSearch.text.toString())
                    // get API
                    currKeywordSearch = etSearch.text.toString()
                    viewModel.resetPaging()
                    searchAdapter.setData(null)
                    viewModel.getDataSearch(etSearch.text.toString())
                    //
                    return true
                }
                return false
            }
        })

        etSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                dispose?.dispose()
                keywordAdapter.setData(null)
                if (p0.isNullOrEmpty()) {
                    ivDone.visibility = View.INVISIBLE
                }
                else {
                    Observable.create(object: ObservableOnSubscribe<String>{
                        override fun subscribe(emitter: ObservableEmitter<String>) {
                            for (i in repoKeyword.items){
                                if(!emitter.isDisposed){
                                    if(filterKeyword(p0.toString(),i)){
                                        emitter.onNext(i)
                                        Log.d("Duydinhcong", i+ Thread.currentThread())
                                    }
                                }else{
                                        emitter.onComplete()
                                        return
                                }
                            }
                            emitter.onComplete()
                        }

                    })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : io.reactivex.Observer<String>{
                            override fun onComplete() {
                                dispose?.dispose()
                            }

                            override fun onSubscribe(d: Disposable) {
                                dispose = d
                            }

                            override fun onNext(t: String) {
                                keywordAdapter.addMoreData(listOf(t))
                            }

                            override fun onError(e: Throwable) {
                                dispose?.dispose()
                            }

                        })
                    keywordAdapter.setKeyword(p0.toString())
                    ivDone.visibility = View.VISIBLE
                }

            }

        })

        keywordAdapter.setOnClickKeywordAdapterListener(object : KeywordAdapter.OnClickKeywordAdapterListener {
            override fun onClick(s: String) {
                etSearch.setText(s)
                keywordAdapter.setData(null)
                // get API
                currKeywordSearch = s
                viewModel.resetPaging()
                viewModel.getDataSearch(etSearch.text.toString())
                //
            }

        })

        searchAdapter.setOnClickSearchAdapterListener(object : SearchAdapter.OnCLickSearchAdapterListener{
            override fun onClickSetting() {
                val bottomSheetFragment = SearchBottomSheetFragment()
                bottomSheetFragment.show((view!!.context as MainActivity).supportFragmentManager, bottomSheetFragment.tag)
            }

        })

        ivDone.setOnClickListener{
            etSearch.setText("")
            ivDone.visibility = View.INVISIBLE
        }

        searchListListener = EndlessRecyclerViewListener(
            viewModel.paging,
            rvSearch.layoutManager as LinearLayoutManager
        )

        searchListListener.setLoadMoreI(object :LoadMoreI{
            override fun loadNextPage() {
                viewModel.getDataSearch(currKeywordSearch)
            }

        })

        rvSearch.addOnScrollListener(searchListListener)
    }
}