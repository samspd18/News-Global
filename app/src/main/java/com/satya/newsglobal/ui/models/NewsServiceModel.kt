package com.satya.newsglobal.ui.models


import com.google.gson.annotations.SerializedName

data class NewsServiceModel(
    @SerializedName("category")
    val category: String,
    @SerializedName("data")
    val `data`: List<Data>
)
data class Data(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("readMoreUrl")
    val readMoreUrl: String?,
    @SerializedName("time")
    val time: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)