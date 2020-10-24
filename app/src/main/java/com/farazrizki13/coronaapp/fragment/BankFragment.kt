package com.farazrizki13.coronaapp.fragment

import android.Manifest
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import com.farazrizki13.coronaapp.R
import kotlinx.android.synthetic.main.fragment_bank.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.lang.IllegalArgumentException

class BankFragment : Fragment() {

    private val  cameraManager by lazy {
        activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private fun <T> cameraCharacteristics (cameraID : String, key : CameraCharacteristics.Key<T>) : T {
        val characteristics = cameraManager.getCameraCharacteristics(cameraID)
        return when (key) {
            CameraCharacteristics.LENS_FACING -> characteristics.get(key)!!
            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP -> characteristics.get(key) !!
            else -> throw IllegalArgumentException("Key not recognized")
        }
    }

    private fun cameraId(lens : Int) : String {
        var deviceId = listOf<String>()
        try {
            val cameraIdList = cameraManager.cameraIdList
            deviceId = cameraIdList.filter { lens == cameraCharacteristics(it, CameraCharacteristics.LENS_FACING) }
        } catch (e: CameraAccessException) {
            Log.e(TAG, e.toString())
        }

        return deviceId[0]
    }

    private fun connectCamera() {
        val deviceId = cameraId(CameraCharacteristics.LENS_FACING_BACK)
        Log.e(TAG, "deviceId: $deviceId")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bank, container, false)
        return view
    }

    companion object {
        const val REQUEST_CAMERA_PERMISSIONS = 100
        private val TAG = BankFragment::class.qualifiedName
        @JvmStatic fun newInstance(): BankFragment {
            val fragment = BankFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private val surfaceListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) = Unit

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture) = true

        override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
            openCamera()
        }
    }

    override fun onResume() {
        super.onResume()
        if (previewTextureView.isAvailable)
            openCamera()
        else
            previewTextureView.surfaceTextureListener = surfaceListener
    }

    private fun openCamera() {
        checkCameraPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @AfterPermissionGranted(REQUEST_CAMERA_PERMISSIONS)
    private fun checkCameraPermission() {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.CAMERA)) {
            Log.d(TAG, "App has camera")
            connectCamera()
        }else {
            EasyPermissions.requestPermissions(activity!!,
                getString(R.string.camera_request_rationale),
                REQUEST_CAMERA_PERMISSIONS,
                Manifest.permission.CAMERA)
        }
    }
}