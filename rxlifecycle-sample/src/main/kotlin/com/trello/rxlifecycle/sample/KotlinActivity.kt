package com.trello.rxlifecycle.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.trello.rxlifecycle.android.ActivityEvent
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle.kotlin.bindUntilEvent
import rx.Observable
import java.util.concurrent.TimeUnit

class KotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, KotlinFragment())
                    .commit()
        }
    }
}
