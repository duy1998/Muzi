package com.example.muzi.title

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.muzi.R
import com.example.muzi.data.TitleItem
import kotlinx.android.synthetic.main.layout_item_title.view.*


class TitleAdapter : RecyclerView.Adapter<TitleAdapter.TitleViewHolder>(){

    private val items:MutableList<TitleItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_title,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(list: List<TitleItem>?) {
        items.clear()
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addMoreData(list: List<TitleItem>?){
        val currCount = items.size
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
            notifyItemRangeInserted(currCount, list.size)
        }
    }

    class TitleViewHolder(view:View) : RecyclerView.ViewHolder(view){
        private val title: TextView = itemView.tvTitle

        private val avatar:ImageView = itemView.ivTitleAvatar

        fun bind(item: TitleItem){
            title.text = item.title
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