package com.example.triveousnewsapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_detailed_page.*

class DetailedPage : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_page)

        val articleUrl= intent.getStringExtra("ArticleUrl")
        articleUrl?.let {
            webView.webViewClient= WebViewClient()
            webView.loadUrl(articleUrl)
            val webViewSettings= webView.settings
            webViewSettings.javaScriptEnabled= true
            webViewSettings.domStorageEnabled= true
        }
    }
}