package com.farazrizki13.coronaapp.fragment

import android.Manifest
import android.content.Context
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.farazrizki13.coronaapp.R
import kotlinx.android.synthetic.main.fragment_bank.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.lang.IllegalArgumentException
import java.util.*

class BankFragment : Fragment() {

    private val MAX_PREVIEW_WIDTH = 1280
    private val MAX_PREVIEW_HEIGHT = 720
    private lateinit var captureSession: CameraCaptureSession
    private lateinit var captureRequesBuilder: CaptureRequest.Builder

    private lateinit var cameraDevice : CameraDevice
    private val deviceSurfaceCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(p0: CameraDevice) {
            cameraDevice = p0
            previewSession()
        }

        override fun onDisconnected(p0: CameraDevice) {
            Log.d(TAG, "camera disconnected")
            p0.close()
        }

        override fun onError(p0: CameraDevice, p1: Int) {
            Log.d(TAG, "camera device error")
            this@BankFragment.activity?.finish()
        }
    }


    private lateinit var backgroundTreat : HandlerThread
    private lateinit var backgroundHandler : Handler

    private val  cameraManager by lazy {
        activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private fun startBackgroundThread() {
        backgroundTreat = HandlerThread("Camara2 Kotlin").also { it.start() }
        backgroundHandler = Handler(backgroundTreat.looper)
    }

    private fun stopBackgroundThread() {
        backgroundTreat.quitSafely()

        try {
            backgroundTreat.join()
        } catch (e: InterruptedException) {
            Log.e(TAG, e.toString())
        }
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
        try {
            cameraManager.openCamera(deviceId, deviceSurfaceCallback, backgroundHandler)
        } catch (e : CameraAccessException) {
            Log.e(TAG, e.toString())
        } catch (e : InterruptedException) {
            Log.e(TAG, "Open camera device interrupted while opened")
        }
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

    private fun previewSession() {
        val surfaceTexture = previewTextureView.surfaceTexture
        surfaceTexture?.setDefaultBufferSize(MAX_PREVIEW_WIDTH, MAX_PREVIEW_HEIGHT)
        val surface = Surface(surfaceTexture)

        captureRequesBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        captureRequesBuilder.addTarget(surface)

        cameraDevice.createCaptureSession(Arrays.asList(surface),
        object : CameraCaptureSession.StateCallback() {
            override fun onConfigureFailed(p0: CameraCaptureSession) {
                Log.e(TAG, "created session failed")
            }
            override fun onConfigured(p0: CameraCaptureSession) {
                captureSession = p0
                captureRequesBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE)
                captureSession.setRepeatingRequest(captureRequesBuilder.build(), null, null)
            }
        }, null)
    }

    private fun closeCamera () {
        if (this::captureSession.isInitialized)
            captureSession.close()
        if (this::cameraDevice.isInitialized)
            cameraDevice.close()
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

        startBackgroundThread()
        if (previewTextureView.isAvailable)
            openCamera()
        else
            previewTextureView.surfaceTextureListener = surfaceListener
    }

    override fun onPause() {
        closeCamera()
        stopBackgroundThread()
        super.onPause()
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