package com.example.triveousnewsapp.retrofit

import com.google.gson.annotations.SerializedName

data class NewsItems(
    @SerializedName("articles")
    var newsArticles: List<Articles>
)
