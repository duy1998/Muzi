package com.example.muzi.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.muzi.R
import com.example.muzi.data.CategoryItem
import kotlinx.android.synthetic.main.layout_item_category.view.*


class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    private val items:MutableList<CategoryItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_category,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<CategoryItem>?) {
        items.clear()
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addMoreData(list: List<CategoryItem>?){
        val currCount = items.size
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
            notifyItemRangeInserted(currCount, list.size)
        }
    }

    class CategoryViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val title: TextView = itemView.tvTitle

        private val channel:TextView = itemView.tvChannel

        private val avatar:ImageView = itemView.ivAvatar

        fun bind(item: CategoryItem){
            title.text = item.title
            channel.text = item.channel
            Glide.with(itemView)
                .load(item.url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.ic_launcher)
                .override(itemView.resources.getDimensionPixelSize(R.dimen.title_avatar_size),
                    itemView.resources.getDimensionPixelSize(R.dimen.title_avatar_size))
                .into(avatar)
        }
    }


}