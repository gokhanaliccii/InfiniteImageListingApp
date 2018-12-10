package com.gokhanaliccii.infiniteimagelisting.ui.images

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gokhanaliccii.infiniteimagelisting.R

class ImageListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.content_frame, ImageListFragment.newInstance())
                    .commit()
        }
    }
}