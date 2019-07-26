package com.example.muzi.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muzi.R
import kotlinx.android.synthetic.main.layout_recommend_keyword.view.*

class KeywordAdapter : RecyclerView.Adapter<KeywordAdapter.KeywordViewHolder>() {
    private val items: MutableList<String> = ArrayList()

    private var keyword = ""

    private var keywordI: OnClickKeywordAdapterListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeywordViewHolder {
        return KeywordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_recommend_keyword,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setKeyword(s: String) {
        keyword = s
    }

    fun setOnClickKeywordAdapterListener(i: OnClickKeywordAdapterListener) {
        keywordI = i
    }

    fun setData(list: List<String>?) {
        items.clear()
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: KeywordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class KeywordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val boldText: TextView = itemView.boldTextView

        private val regularText: TextView = itemView.regularTextView

        fun bind(item: String) {
            boldText.text = keyword
            var tmp = item.substring(keyword.length)
            regularText.text = tmp
            itemView.setOnClickListener {
                keywordI?.onClick(item)
            }
        }
    }

    interface OnClickKeywordAdapterListener {
        fun onClick(s: String)
    }
}

