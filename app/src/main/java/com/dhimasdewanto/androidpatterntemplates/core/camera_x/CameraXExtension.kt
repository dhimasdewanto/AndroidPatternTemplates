package com.dhimasdewanto.androidpatterntemplates.core.camera_x

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.extensions.BokehImageCaptureExtender
import androidx.camera.extensions.HdrImageCaptureExtender

class CameraXExtension(
    private val imageCaptureBuilder: ImageCapture.Builder,
    private val cameraSelector: CameraSelector
) {
    fun tryEnableBokeh(isBokehEnabled: Boolean): Boolean {
        val bokehImageCapture =
            BokehImageCaptureExtender.create(imageCaptureBuilder)

        val isBokehAvailable = bokehImageCapture.isExtensionAvailable(cameraSelector)
        if (isBokehAvailable && isBokehEnabled) {
            bokehImageCapture.enableExtension(cameraSelector)
            return true
        }

        return false
    }

    fun tryEnableHdr(isHdrEnabled: Boolean): Boolean {
        val hdrImageCapture =
            HdrImageCaptureExtender.create(imageCaptureBuilder)

        val isHdrAvailable = hdrImageCapture.isExtensionAvailable(cameraSelector)
        if (isHdrAvailable && isHdrEnabled) {
            hdrImageCapture.enableExtension(cameraSelector)
            return true
        }

        return false
    }
}