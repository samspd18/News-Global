package com.satya.newsglobal.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.satya.newsglobal.R
import com.satya.newsglobal.databinding.CategoryNewsListBinding
import com.satya.newsglobal.ui.models.Data
import com.squareup.picasso.Picasso

class CategoryNewsListAdapter: RecyclerView.Adapter<CategoryNewsListAdapter.MainViewHolder>() {
    private var news = mutableListOf<Data>()
    private var mainViewHolderType = ""


    fun setCategoryWiseNews(news: List<Data>) {
        this.news = news.toMutableList()
        notifyDataSetChanged()
    }

    class MainViewHolder(var binding: CategoryNewsListBinding) : RecyclerView.ViewHolder(binding.root) {
        var newsTitle = ""
        var author = ""
        var content = ""
        var readMoreUrl = ""
        var shareUrl = ""
        var imageUrl = ""
        var date = ""
        var time = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryNewsListBinding.inflate(inflater,parent,false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        val newsData = news[position]
        holder.newsTitle = newsData.title
        holder.imageUrl = newsData.imageUrl
        holder.author = newsData.author
        holder.content = newsData.content
        holder.readMoreUrl = newsData.readMoreUrl.toString()
        holder.shareUrl = newsData.url
        holder.date = newsData.date
        holder.time = newsData.time

        holder.binding.newsTitle.text = newsData.title
        val imageUrl = newsData.imageUrl
        Picasso.get()
            .load(imageUrl)
            .noFade()
            .into(holder.binding.newsImage)

        holder.binding.root.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("title", holder.newsTitle)
            bundle.putString("imageUrl",holder.imageUrl)
            bundle.putString("author",holder.author)
            bundle.putString("content",holder.content)
            bundle.putString("readMoreUrl",holder.readMoreUrl)
            bundle.putString("shareUrl",holder.shareUrl)
            bundle.putString("date",holder.date)
            bundle.putString("time",holder.time)

            val navigation = holder.binding.view.findNavController()
            navigation.navigate(R.id.navigation_newsDetails,bundle)
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }

}