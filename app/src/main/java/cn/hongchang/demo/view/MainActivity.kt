package cn.hongchang.demo.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import cn.hongchang.demo.R
import cn.hongchang.demo.databinding.MainActivityBinding
import cn.hongchang.demo.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private var dataBinding: MainActivityBinding? = null
    private var mainViewModel: MainViewModel? = null

    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    private val mPermissionList = mutableListOf<String>()
    private val ACCESS_FINE_ERROR_CODE = 0x0245

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mainViewModel = MainViewModel()
        dataBinding?.mainViewModel = mainViewModel
        lifecycle.addObserver(mainViewModel as LifecycleObserver)

        requestPermissions()
    }

    /**
     * 权限
     */
    private fun requestPermissions() {
        val permissions = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.RECORD_AUDIO)

        mPermissionList.clear()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(this, permission!!) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permission)
                }
            }

            //未授予的权限为空，表示都授予了
            if (mPermissionList.isEmpty()) {
                showToast("已经授权")
            } else {
                //将List转为数组
                ActivityCompat.requestPermissions(this, mPermissionList.toTypedArray(), ACCESS_FINE_ERROR_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (i in grantResults.indices) {
            val showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]!!)
            if (showRequestPermission) {
                showToast("权限未申请")
            }
        }
    }

    protected fun showToast(toastInfo: String?) {
        Toast.makeText(this, toastInfo, Toast.LENGTH_LONG).show()
    }

}