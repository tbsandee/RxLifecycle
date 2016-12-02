package com.trello.rxlifecycle.sample

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by tsandee on 12/1/16.
 */
class KotlinFragment : RxFragment() {

    companion object {
        private val TAG = "KotlinFragment"
    }

    var inDestroyView = false
    var inOnStop = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Specifically bind this until onPause()
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnUnsubscribe {
                    if(inDestroyView) {
                        Log.i(TAG, "Unsubscribing subscription from onViewCreated()")
                    } else if(inOnStop) {
                        Log.i(TAG, "BUG!!! UNSUBSCRIBED FROM ONSTOP")
                    } else {
                        throw IllegalStateException("unsubscribed from somewhere else")
                    }
                }
                .bindToLifecycle(this)
                .subscribe { num -> Log.i(TAG, "Started in onViewCreated(), running until onViewDestroy(): " + num!!) }
    }

    override fun onDestroyView() {
        inDestroyView = true
        super.onDestroyView()
        inDestroyView = false
    }

    override fun onStop() {
        inOnStop = true
        super.onStop()
        inOnStop = false
    }
}