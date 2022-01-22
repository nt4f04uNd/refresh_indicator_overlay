package com.example.refresh_indicator_overlay

import android.app.Activity
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : Activity() {
    lateinit var flutterEngine: FlutterEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault())
        val flutterView: FlutterView = findViewById(R.id.flutter_view)
        flutterView.attachToFlutterEngine(flutterEngine)

        val pullToRefresh = findViewById<CustomSwipeRefreshLayout>(R.id.pull_to_refresh)
        pullToRefresh.bindFlutterView(flutterView)
        pullToRefresh.setProgressBackgroundColorSchemeColor(0x88FFFFFF.toInt())
        pullToRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                pullToRefresh.isRefreshing = false
            }, 1000)
        }
    }

    override fun onDestroy() {
        flutterEngine.destroy()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        flutterEngine.lifecycleChannel.appIsInactive()
    }

    override fun onPostResume() {
        super.onPostResume()
        flutterEngine.lifecycleChannel.appIsResumed()
    }

    override fun onBackPressed() {
        flutterEngine.navigationChannel.popRoute()
        super.onBackPressed()
    }

    override fun onStop() {
        flutterEngine.lifecycleChannel.appIsPaused()
        super.onStop()
    }
}

class CustomSwipeRefreshLayout(context : Context, attributeSet: AttributeSet) : SwipeRefreshLayout(context, attributeSet) {
    lateinit var flutterView: FlutterView
    fun bindFlutterView(view: FlutterView) {
        flutterView = view
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        flutterView.dispatchTouchEvent(ev)
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        flutterView.dispatchTouchEvent(ev)
        return super.onTouchEvent(ev)
    }
}
