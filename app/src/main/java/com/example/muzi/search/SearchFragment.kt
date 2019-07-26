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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muzi.MainActivity
import com.example.muzi.R
import com.example.muzi.data.SearchItem
import com.example.muzi.widget.EndlessRecyclerViewListener
import com.example.muzi.widget.LoadMoreI
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_toolbar_search.*


class SearchFragment : Fragment() {

    private val keywordAdapter = KeywordAdapter()

    private val searchAdapter = SearchAdapter()

    private var repoKeyword = arrayListOf<String>()

    private var currKeywordSearch: String = ""

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchListListener: EndlessRecyclerViewListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        repoKeyword.add("mmmsd")
        repoKeyword.add("abb")
        repoKeyword.add("aaad")
        repoKeyword.add("aaaas")

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        viewModel.init()

        initRecyclerView()

        initListener()

    }

//    override fun onAttach(activity: Activity) {
//        myContext = activity as FragmentActivity
//        super.onAttach(activity)
//    }

    fun filterKeyword(s:String) : List<String>{
        val list:MutableList<String> = ArrayList()
        for(i in repoKeyword){
            if(i.length>= s.length){
                if (i.substring(0,s.length).compareTo(s)==0) {
                    list.add(i)
                }
            }
        }
        return list
    }

    private fun initRecyclerView(){
        keywordRecyclerView.layoutManager = LinearLayoutManager(this.context)
        keywordRecyclerView.adapter = keywordAdapter

        searchRecyclerView.layoutManager = LinearLayoutManager(this.context)
        searchRecyclerView.adapter = searchAdapter

        viewModel.searchList.observe(this,
            Observer<List<SearchItem>> { t ->
                if(searchAdapter.itemCount == 0) {
                    searchAdapter.setData(t)
                    keywordAdapter.setData(null)
                    Log.d("Loadmore","First Data")
                } else{
                    searchAdapter.addMoreData(t)
                    Log.d("Loadmore","Next Data")
                }
            })
    }

    private fun initListener(){
        searchEditText.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_SEARCH) {
                    // get API
                    currKeywordSearch = searchEditText.text.toString()
                    viewModel.resetPaging()
                    viewModel.getDataSearch(searchEditText.text.toString())
                    //
                    return true
                }
                return false
            }
        })

        searchEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrEmpty()) {
                    doneImage.visibility = View.INVISIBLE
                    keywordAdapter.setData(null)
                } else {
                    keywordAdapter.setKeyword(p0.toString())
                    keywordAdapter.setData(filterKeyword(p0.toString()))
                    doneImage.visibility = View.VISIBLE
                }
            }

        })

        keywordAdapter.setOnClickKeywordAdapterListener(object : KeywordAdapter.OnClickKeywordAdapterListener {
            override fun onClick(s: String) {
                searchEditText.setText(s)
                keywordAdapter.setData(null)
                // get API
                currKeywordSearch = s
                viewModel.resetPaging()
                viewModel.getDataSearch(searchEditText.text.toString())
                //
            }

        })

        searchAdapter.setOnClickSearchAdapterListener(object : SearchAdapter.OnCLickSearchAdapterListener{
            override fun onClickSetting() {
                val bottomSheetFragment = SearchBottomSheetFragment()
                bottomSheetFragment.show((view!!.context as MainActivity).supportFragmentManager, bottomSheetFragment.tag)
            }

        })

        doneImage.setOnClickListener{
            searchEditText.setText("")
            doneImage.visibility = View.INVISIBLE
        }

        searchListListener = EndlessRecyclerViewListener(
            viewModel.paging,
            searchRecyclerView.layoutManager as LinearLayoutManager
        )

        searchListListener.setLoadMoreI(object :LoadMoreI{
            override fun loadNextPage() {
                Log.d("LoadMore", "Loading")
                viewModel.getDataSearch(currKeywordSearch)
            }

        })

        searchRecyclerView.addOnScrollListener(searchListListener)
    }
}