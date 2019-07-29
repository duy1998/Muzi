package com.example.muzi.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.muzi.R
import com.example.muzi.data.SearchItem
import com.example.muzi.utils.FormatUtil
import kotlinx.android.synthetic.main.layout_item_search.view.*

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    private val items : MutableList<SearchItem> = ArrayList()

    lateinit var onCLickSearchAdapterListener: OnCLickSearchAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_item_search,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.bind(items[position])
    }

    fun setData(list: List<SearchItem>?) {
        items.clear()
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addMoreData(list: List<SearchItem>?){
        val currCount = items.size
        if (!list.isNullOrEmpty()) {
            items.addAll(list)
            notifyItemRangeInserted(currCount, list.size)
        }
    }

    fun setOnClickSearchAdapterListener(onCLickSearchAdapterListener: OnCLickSearchAdapterListener){
        this.onCLickSearchAdapterListener = onCLickSearchAdapterListener
    }

    inner class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        private var thumnail = itemView.thumnailView
        private var avatar = itemView.ivAvatar
        private var duration = itemView.tvDuration
        private var title = itemView.tvTitle
        private var channel = itemView.tvChannel
        private var viewCount = itemView.tvViewCount
        private var setting = itemView.ivSetting
        fun bind(item: SearchItem){
//            thumnail.initialize(Constant.API_KEY,object : YouTubeThumbnailView.OnInitializedListener{
//                override fun onInitializationSuccess(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader?) {
//                    p1!!.setVideo(item.videoID)
//                    p1.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener{
//                        override fun onThumbnailLoaded(p0: YouTubeThumbnailView?, p2: String?) {
//                            p1.release()
//                        }
//
//                        override fun onThumbnailError(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader.ErrorReason?
//                        ) {
//                        }
//
//                    })
//                    readyForLoadingYoutubeThumbnail = true
//                }
//
//                override fun onInitializationFailure(p0: YouTubeThumbnailView?, p1: YouTubeInitializationResult?) {
//                    readyForLoadingYoutubeThumbnail = true
//                }
//
//            })
            Glide.with(itemView)
                .load(item.linkThumbnail)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.ic_launcher)
                .override(itemView.resources.getDimensionPixelSize(R.dimen.search_avatar_width_size),
                    itemView.resources.getDimensionPixelSize(R.dimen.search_avatar_height_size))
                .into(avatar)

            duration.text = FormatUtil.formatDuration(item.duration)
            title.text = item.title
            channel.text = item.channel
            viewCount.text = FormatUtil.formatViewCount(item.viewCount) +" "+ itemView.resources.getString(R.string.views)
            setting.setOnClickListener{
                onCLickSearchAdapterListener.onClickSetting()
            }
        }
    }

interface OnCLickSearchAdapterListener{
    fun onClickSetting()
}

}