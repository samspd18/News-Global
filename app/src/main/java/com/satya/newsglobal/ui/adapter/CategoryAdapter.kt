package com.satya.newsglobal.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satya.newsglobal.databinding.CategoryListBinding
import com.satya.newsglobal.databinding.NewsListLayoutBinding
import com.satya.newsglobal.ui.constants.Category
import com.satya.newsglobal.ui.models.Data
import com.squareup.picasso.Picasso

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var category = mutableListOf<Category>()

    fun setCategories(category:Array<Category>) {
        this.category = category.toMutableList()
        notifyDataSetChanged()
    }

    class CategoryViewHolder(var binding: CategoryListBinding) : RecyclerView.ViewHolder(binding.root) {
        var categoryName = ""
        var imageUrl = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryListBinding.inflate(inflater,parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val categoryData = category[position]
        holder.binding.eachCategoryName.text = categoryData.name
        val imageUrl = categoryData.imageUrl
        Picasso.get()
            .load(imageUrl)
            .noFade()
            .into(holder.binding.eachCategoryImage)
    }

    override fun getItemCount(): Int {
       return category.count()
    }
}