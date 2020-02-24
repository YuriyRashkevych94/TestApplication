package com.example.testapplication.screen.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.data.Article
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    companion object {
        private const val BUNDLE_ARTICLE = "article"

        fun getStartIntent(fromContext: Context, article: Article): Intent {
            val intent = Intent(fromContext, DetailsActivity::class.java)
            intent.putExtra(BUNDLE_ARTICLE, article)

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        textContent.movementMethod = ScrollingMovementMethod()

        val model = intent.getSerializableExtra(BUNDLE_ARTICLE) as Article

        // title
        supportActionBar?.title = model.title

        // thumbnail
        if (model.thumbnail != null && model.thumbnail.isNotEmpty()) {
            Glide.with(this@DetailsActivity).load(model.thumbnail).into(imageThumbnail)
            imageThumbnail.visibility = View.VISIBLE
        } else {
            imageThumbnail.visibility = View.GONE
        }

        // content
        textContent.text = model.content
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
