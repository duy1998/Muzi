package com.example.muzi.title

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.muzi.R
import com.example.muzi.data.Resource
import com.example.muzi.data.TitleItem
import com.example.muzi.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_title.*


class TitleFragment : Fragment(){

    private val titleAdapter = TitleAdapter()

    lateinit var viewModel : TitleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_title, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(TitleViewModel::class.java)

        viewModel.init()

        initView()
        initListener()
    }

    fun initView(){
        rvList.adapter = titleAdapter
        rvList.layoutManager = GridLayoutManager(this.context,2)
        val spanCount = 2 // 3 columns
        val spacing = 30 // 30px
        val includeEdge = true
        rvList.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))
        viewModel.getDataTitle()


        viewModel.resourceData.observe(this,
            Observer<Resource<List<TitleItem>>> { t ->
                when(t.status){
                    Resource.LOADING -> {

                    }
                    Resource.SUCCESSED -> {
                        titleAdapter.setData(t.data)
                    }
                    Resource.NETWORK_ERROR -> {

                    }
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu_title_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    fun initListener(){

    }
}