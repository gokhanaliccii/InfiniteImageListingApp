package com.gokhanaliccii.infiniteimagelisting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gokhanaliccii.infiniteimagelisting.ui.images.ImageListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.content_frame, ImageListFragment.newInstance())
            .commit()
    }
}