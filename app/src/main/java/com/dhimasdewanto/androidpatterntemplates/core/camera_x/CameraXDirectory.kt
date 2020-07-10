package com.dhimasdewanto.androidpatterntemplates.core.camera_x

import androidx.fragment.app.Fragment
import com.dhimasdewanto.androidpatterntemplates.R
import java.io.File

class CameraXDirectory(
    private val fragment: Fragment
) {
    val outputDirectory: File
        get() {
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