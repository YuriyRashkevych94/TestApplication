package com.example.testapplication.screen.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.R
import com.example.testapplication.application.Application
import com.example.testapplication.data.Article
import com.example.testapplication.data.Error
import com.example.testapplication.screen.details.DetailsActivity
import com.example.testapplication.screen.main.adapter.ArticlesAdapter
import com.example.testapplication.screen.main.adapter.DividerItemDecoration
import com.example.testapplication.screen.main.di.MainModule
import com.example.testapplication.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var animation: Animation
    private lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject(this@MainActivity)

        supportActionBar?.title = getString(R.string.kotlin_news)
        recyclerSetup()

        animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.slide_up)
        viewModel.articlesLiveData.observe(this@MainActivity, articlesObserver)
        viewModel.error.observe(this@MainActivity, errorObserver)

        viewModel.pullArticles()
    }

    private fun recyclerSetup() {
        // layout manager
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        recyclerArticles.layoutManager = layoutManager
        // divider
        val divider = DividerItemDecoration(Utils.dpToPx(1))
        recyclerArticles.addItemDecoration(divider)
        // adapter
        adapter = ArticlesAdapter(this@MainActivity)
        adapter.clickListener = articleClickListener
        recyclerArticles.adapter = adapter
    }

    private fun inject(activity: MainActivity) {
        val subComponent = Application.component.plusMainComponent(MainModule(activity))
        subComponent.inject(activity)
    }

    private val articlesObserver: Observer<List<Article>> = Observer {
        adapter.update(it)
        recyclerArticles.scheduleLayoutAnimation()
    }

    private val errorObserver: Observer<Error> = Observer {
        adapter.update(arrayListOf())

        // show error
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle(it.title)
        builder.setMessage(it.message)
        val dialog = builder.create()
        dialog.show()
    }

    private val articleClickListener = object : ArticlesAdapter.OnArticleClickListener {
        override fun onRepositoryClicked(article: Article) {
            if (article.content != null && article.content.isNotEmpty()) {
                startActivity(DetailsActivity.getStartIntent(this@MainActivity, article))
                return
            }

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle(getString(R.string.no_article_desc_title))
            builder.setMessage(getString(R.string.no_article_desc_message))
            builder.setPositiveButton(getString(R.string.action_yes)) { dialogInterface, i ->
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                startActivity(intent)
                dialogInterface.dismiss()
            }
            builder.setNegativeButton(getString(R.string.action_no)) { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }
}
