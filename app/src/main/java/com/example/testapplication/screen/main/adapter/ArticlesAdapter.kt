package com.example.testapplication.screen.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.data.Article
import kotlinx.android.synthetic.main.view_article.view.*

class ArticlesAdapter(context: Context) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private var list = listOf<Article>()

    interface OnArticleClickListener {
        fun onRepositoryClicked(article: Article)
    }

    var clickListener: OnArticleClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.view_article, parent, false)
        return ViewHolder(view, clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun update(list: List<Article>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        view: View,
        private val clickListener: OnArticleClickListener?
    ) : RecyclerView.ViewHolder(view) {

        lateinit var model: Article

        init {
            view.setOnClickListener { clickListener?.onRepositoryClicked(model) }
        }

        fun bind(model: Article) {
            this.model = model
            itemView.textTitle.text = model.title
            if(model.thumbnail != null && model.thumbnail.isNotEmpty()) {
                Glide.with(itemView.context).load(model.thumbnail).into(itemView.imageThumbnail)
                itemView.imageThumbnail.visibility = View.VISIBLE
            } else {
                itemView.imageThumbnail.visibility = View.GONE
            }
        }
    }

}