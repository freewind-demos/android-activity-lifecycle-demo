package demos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity 生命周期演示主界面
 * 展示 Activity 的各个生命周期方法
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "ActivityLifecycle"
    private lateinit var statusText: TextView
    private val lifecycleEvents = StringBuilder()

    // Activity 创建时调用
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        val startSecondBtn = findViewById<Button>(R.id.startSecondBtn)
        val clearBtn = findViewById<Button>(R.id.clearBtn)

        addLifecycleEvent("onCreate: Activity 正在创建")

        // 启动第二个 Activity
        startSecondBtn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        // 清空显示的事件
        clearBtn.setOnClickListener {
            lifecycleEvents.clear()
            updateStatusText()
        }

        Log.d(TAG, "onCreate called")
    }

    // Activity 即将可见时调用
    override fun onStart() {
        super.onStart()
        addLifecycleEvent("onStart: Activity 即将可见")
        Log.d(TAG, "onStart called")
    }

    // Activity 变为可见时调用
    override fun onResume() {
        super.onResume()
        addLifecycleEvent("onResume: Activity 已可见，可以交互")
        Log.d(TAG, "onResume called")
    }

    // Activity 失去焦点时调用
    override fun onPause() {
        super.onPause()
        addLifecycleEvent("onPause: Activity 失去焦点")
        Log.d(TAG, "onPause called")
    }

    // Activity 不再可见时调用
    override fun onStop() {
        super.onStop()
        addLifecycleEvent("onStop: Activity 不可见")
        Log.d(TAG, "onStop called")
    }

    // Activity 即将销毁时调用
    override fun onDestroy() {
        super.onDestroy()
        addLifecycleEvent("onDestroy: Activity 即将销毁")
        Log.d(TAG, "onDestroy called")
    }

    // Activity 重新创建时调用（如屏幕旋转）
    override fun onRestart() {
        super.onRestart()
        addLifecycleEvent("onRestart: Activity 重新启动")
        Log.d(TAG, "onRestart called")
    }

    // 保存 Activity 状态
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("lifecycle_events", lifecycleEvents.toString())
        Log.d(TAG, "onSaveInstanceState called")
    }

    // 恢复 Activity 状态
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val events = savedInstanceState.getString("lifecycle_events", "")
        if (events.isNotEmpty()) {
            lifecycleEvents.clear()
            lifecycleEvents.append(events)
            updateStatusText()
        }
        addLifecycleEvent("onRestoreInstanceState: 恢复状态")
        Log.d(TAG, "onRestoreInstanceState called")
    }

    private fun addLifecycleEvent(event: String) {
        lifecycleEvents.append(event).append("\n")
        updateStatusText()
    }

    private fun updateStatusText() {
        statusText.text = lifecycleEvents.toString()
    }
}
