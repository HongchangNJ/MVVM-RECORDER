package cn.hongchang.demo.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import cn.hongchang.demo.utils.AudioRecorder
import cn.hongchang.demo.utils.AudioStatus
import cn.hongchang.demo.utils.Constant
import cn.hongchang.demo.utils.IAudioCallback
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel : ViewModel(), LifecycleObserver, IAudioCallback {
    private var audioRecorder: AudioRecorder? = null

    init {
        audioRecorder = AudioRecorder.getInstance(this)
    }

    var recordListener = View.OnClickListener {
        if (audioRecorder?.status === AudioStatus.STATUS_NO_READY) {
            Log.d(Constant.TAG, "record start")
            //初始化录音
            val fileName = SimpleDateFormat("yyyyMMddhhmmss", Locale.CHINA).format(Date())
            audioRecorder?.createDefaultAudio(fileName)
            audioRecorder?.startRecord()
        } else {
            audioRecorder?.stopRecord()

            /*if (audioRecorder!!.status === AudioStatus.STATUS_START) {
                Log.d(Constant.TAG, "record pause")
                audioRecorder?.pauseRecord()
            } else {
                Log.d(Constant.TAG, "record stop")
            }*/
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
        Log.d(Constant.TAG, "record and wrapper success")
    }
}