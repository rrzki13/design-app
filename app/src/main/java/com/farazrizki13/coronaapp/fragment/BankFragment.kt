package com.farazrizki13.coronaapp.fragment

import android.Manifest
import android.graphics.SurfaceTexture
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

class BankFragment : Fragment() {

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
        }else {
            EasyPermissions.requestPermissions(activity!!,
                getString(R.string.camera_request_rationale),
                REQUEST_CAMERA_PERMISSIONS,
                Manifest.permission.CAMERA)
        }
    }
}