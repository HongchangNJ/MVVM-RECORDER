package cn.hongchang.demo.viewmodel

import android.os.Message
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import cn.hongchang.demo.RApplication
import cn.hongchang.demo.utils.AudioRecorder
import cn.hongchang.demo.utils.AudioStatus
import cn.hongchang.demo.utils.Constant
import cn.hongchang.demo.utils.IAudioCallback
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel(), LifecycleObserver, IAudioCallback {
    private var audioRecorder: AudioRecorder? = null
    private var mTime = 0f
    private val mHandler = object : android.os.Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                0 -> {
                    showToast(msg?.obj as String)
                }

                1 -> {
                    Log.d(Constant.TAG, "record and wrapper success")
                }
            }
        }
    }


    init {
        audioRecorder = AudioRecorder.getInstance(this)
    }

    var recordListener = View.OnLongClickListener {
        Log.d(Constant.TAG, "long click")
        if (audioRecorder?.status === AudioStatus.STATUS_NO_READY) {
            Log.d(Constant.TAG, "record start")
            //初始化录音
            val fileName = SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA).format(Date())
            audioRecorder?.createDefaultAudio(fileName)
            audioRecorder?.startRecord()
            Log.d(Constant.TAG, "state after call startRecord: ${audioRecorder?.status}")

            // 100ms后开始
            mHandler.postDelayed(object : Runnable {
                override fun run() {
                    Thread(mGetVoiceLevelRunnable).start()
                }

            }, 100L);
            false
        } else {
            stop()
            false
        }
    }

    var onTouchListener = View.OnTouchListener { v, event ->

        val action = event.action
        when (action) {
            MotionEvent.ACTION_UP -> {
                Log.d(Constant.TAG, "touch action up")
                if (audioRecorder?.status === AudioStatus.STATUS_START) {
                    if (0.8f.compareTo(mTime) > 0f) {
                        showToast("录制时间太短")
                        audioRecorder?.cancel()
                    } else {
                        showToast("结束录制")
                        stop()
                    }
                    false
                } else {
                    stop()
                    false
                }
            }
        }
        false
    }

    private fun stop() {
        if (audioRecorder?.status === AudioStatus.STATUS_NO_READY || audioRecorder?.status == AudioStatus.STATUS_READY) {
            Log.d(Constant.TAG, "stop fail: status error")
        } else {
            audioRecorder?.stopRecord()
            mTime = 0f
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Log.d(Constant.TAG, "onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Log.d(Constant.TAG, "onPause")
        if (audioRecorder?.status === AudioStatus.STATUS_START) {
            audioRecorder?.pauseRecord()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Log.d(Constant.TAG, "onDestroy")
        audioRecorder?.release()
        audioRecorder?.releaseAudioTrack()
    }

    override fun showPlay(filePath: String?) {
        //Log.d(Constant.TAG, "record and wrapper success")
        val msg = Message()
        msg.what = 1
        mHandler.sendMessage(msg)
    }

    // 获取音量大小的runnable
    private val mGetVoiceLevelRunnable = object : Runnable {
        override fun run() {
            Log.d(Constant.TAG, "--------")
            while (audioRecorder?.status === AudioStatus.STATUS_START) {
                try {
                    //最长mMaxRecordTimes
                    if (mTime > 10) {
                        Log.d(Constant.TAG, "到达10s，结束录制")
                        val msg = Message()
                        msg.what = 0
                        msg.obj = "\"录制时间到达10s，结束录制\""
                        mHandler.sendMessage(msg)
                        stop()
                        return
                    }
                    Thread.sleep(100)
                    mTime += 0.1f
                    //mStateHandler.sendEmptyMessage(com.gaoyuan.weixinrecord.manager.AudioRecordButton.MSG_VOICE_CHANGE)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }

    protected fun showToast(toastInfo: String?) {
        Toast.makeText(RApplication.INSTANCE, toastInfo, Toast.LENGTH_LONG).show()
    }
}