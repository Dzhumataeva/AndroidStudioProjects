package com.example.newsus


data class News(val articles: ArrayList<Article>)

data class Article(
    var author: String,
    var content: String,
    var description: String,
    var publishedAt: String,
    val source: Source,
    var title: String,
    var url: String,
    var urlToImage: String
    )

data class Source(
    val id: String,
    var name: String
)