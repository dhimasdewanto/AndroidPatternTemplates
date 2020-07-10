package com.dhimasdewanto.androidpatterntemplates.features.ui.camerax

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dhimasdewanto.androidpatterntemplates.R
import java.io.File
import java.util.*

class CameraXBuilder(
    private val cameraViewFinder: PreviewView,
    private val captureMode: Int = ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY,
    private val lensFacing: Int = CameraSelector.LENS_FACING_BACK
) {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    }

    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    private lateinit var camera: Camera
    private lateinit var outputDirectory: File

    fun takePhoto(
        fragment: Fragment,
        fileName: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis()) + ".jpg",
        onImageSavedResponse: (output: ImageCapture.OutputFileResults) -> Unit = fun(_) {}
    ) {
        // Create timestamped output file to hold the image
        val photoFile = File(outputDirectory, fileName)

        // Create output options object which contains file + metadata
        val outputOptions =
            ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Setup image capture listener which is triggered after photo has been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(fragment.requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Log.d(TAG, msg)
                    onImageSavedResponse(output)
                }
            })
    }

    fun initCameraX(fragment: Fragment) {
        val baseContext = fragment.requireActivity().baseContext
        if (allPermissionsGranted(baseContext)) {
            startCamera(fragment)
            return
        }

        fragment.requestPermissions(
            REQUIRED_PERMISSIONS,
            REQUEST_CODE_PERMISSIONS
        )

        outputDirectory = getOutputDirectory(fragment)
    }

    fun setPermission(
        fragment: Fragment,
        requestCode: Int,
        onNotGranted: () -> Unit
    ) {
        if (requestCode != REQUEST_CODE_PERMISSIONS) {
            return
        }

        val baseContext = fragment.requireActivity().baseContext
        if (!allPermissionsGranted(baseContext)) {
            onNotGranted()
        }

        startCamera(fragment)
    }

    private fun allPermissionsGranted(baseContext: Context) = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera(fragment: Fragment) {
        val cameraProviderFuture =
            ProcessCameraProvider.getInstance(fragment.requireContext())

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Init Preview
            preview = Preview.Builder().build()

            // Init Image Capture
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(captureMode)
                .build()

            // Select back camera
            val cameraSelector =
                CameraSelector.Builder()
                    .requireLensFacing(lensFacing)
                    .build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    fragment, cameraSelector, preview, imageCapture
                )
                preview.setSurfaceProvider(cameraViewFinder.createSurfaceProvider())
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(fragment.activity))
    }

    private fun getOutputDirectory(fragment: Fragment): File {
        val filesDir = fragment.requireActivity().filesDir

        val mediaDir = fragment.activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, fragment.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        if (mediaDir != null && mediaDir.exists()) {
            return mediaDir
        }

        return filesDir
    }

}