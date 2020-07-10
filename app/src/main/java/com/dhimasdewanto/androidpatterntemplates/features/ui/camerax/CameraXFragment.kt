package com.dhimasdewanto.androidpatterntemplates.features.ui.camerax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhimasdewanto.androidpatterntemplates.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_camera_x.*

class CameraXFragment : Fragment() {
    private lateinit var viewModel: CameraXViewModel
    private lateinit var cameraXBuilder: CameraXBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_camera_x, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CameraXViewModel::class.java)

        cameraXBuilder = CameraXBuilder(view_finder)
        cameraXBuilder.initCameraX(fragment)

        camera_capture_button.setOnClickListener { cameraXBuilder.takePhoto(fragment) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        cameraXBuilder.setPermission(fragment, requestCode) {
            findNavController().popBackStack()
        }
    }

}