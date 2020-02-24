package com.example.testapplication.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Feed constructor(val data: FeedData)

    class FeedData(val children: List<Children>)

        class Children(val data: Article)

            data class Article constructor(val title: String,
                                           val thumbnail: String?,
                                           val url: String,
                                           @SerializedName("selftext") val content: String?) : Serializable