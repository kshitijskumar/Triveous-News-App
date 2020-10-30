package com.example.triveousnewsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.triveousnewsapp.R
import com.example.triveousnewsapp.retrofit.Articles
import kotlinx.android.synthetic.main.news_items.view.*

class NewsAdapter(private var newsItems: List<Articles>, private val listener: OnNewsClicked) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>(){

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.news_items, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem= newsItems[position]
        val currentItemDateTimeSplit= currentItem.publishedAt?.split("T")

        Glide.with(holder.itemView).load(currentItem.imageUrl).into(holder.itemView.ivImage)
        holder.itemView.tvTitle.text= currentItem.title
        holder.itemView.tvDescription.text= currentItem.description
        holder.itemView.tvDate.text= currentItemDateTimeSplit?.get(0) ?: "Y/M/D"
        holder.itemView.tvTime.text= currentItemDateTimeSplit?.get(1)?.replace("Z", "") ?: "-/-/-"

        holder.itemView.setOnClickListener {
            listener.newsItemClicked(currentItem.url!!)
        }
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    interface OnNewsClicked{
        fun newsItemClicked(articleUrl: String)
    }
}