package com.dhimasdewanto.androidpatterntemplates.features.ui.camerax

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhimasdewanto.androidpatterntemplates.R
import com.dhimasdewanto.androidpatterntemplates.core.camera_x.CameraXBuilder
import com.dhimasdewanto.androidpatterntemplates.features.ui.MainActivity
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
        hideSystemUI()
        onBackShowSystemUI()

        cameraXBuilder = CameraXBuilder(fragment, view_finder)
        button_camera_capture.setOnClickListener {
            cameraXBuilder.takePhoto {
                Toast.makeText(context, "Success take photo!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        cameraXBuilder.setPermission(requestCode) {
            findNavController().popBackStack()
        }
    }

    private fun hideSystemUI() {
        requireActivity()
            .window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        (requireActivity() as MainActivity).supportActionBar!!.hide()
    }

    private fun onBackShowSystemUI() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity()
                .window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            (requireActivity() as MainActivity).supportActionBar!!.show()
            findNavController().popBackStack()
        }
    }

}