package com.example.muzi.category

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muzi.R
import com.example.muzi.data.CategoryItem
import com.example.muzi.data.Resource
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_category.*


class CategoryFragment : Fragment(){

    private val titleAdapter = CategoryAdapter()

    private lateinit var viewModel : CategoryViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         return inflater.inflate(R.layout.fragment_category, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)

        viewModel.init()

        initView()
        initListener()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun initView(){
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { p0, p1 ->
            if (Math.abs(p1)-p0!!.totalScrollRange == 0) {
                tvView.visibility = View.INVISIBLE
            } else {
                tvView.visibility = View.VISIBLE
            }
        })
        rvList.adapter = titleAdapter

        rvList.layoutManager = LinearLayoutManager(this.context)
        viewModel.getDataCategory()


        viewModel.resourceData.observe(this,
            Observer<Resource<List<CategoryItem>>> { t ->
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