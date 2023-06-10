package com.dwi.maurea.ui.scan.detection

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dwi.maurea.R
import com.dwi.maurea.databinding.ActivityDetectionBinding
import com.dwi.maurea.utils.Constanta.MODEL
import com.dwi.maurea.utils.ObjectDetectorUtils
import com.dwi.maurea.utils.ObjectDetectorUtils.Companion.MODEL_DRAGON_FRUIT
import com.dwi.maurea.utils.ObjectDetectorUtils.Companion.MODEL_MANGO
import com.dwi.maurea.utils.ObjectDetectorUtils.Companion.MODEL_PAPAYA
import com.dwi.maurea.utils.ObjectDetectorUtils.Companion.MODEL_TOMATO
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.LinkedList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetectionActivity : AppCompatActivity(), ObjectDetectorUtils.DetectorListener {
    private lateinit var binding: ActivityDetectionBinding
    private lateinit var objectDetectorUtils: ObjectDetectorUtils
    private lateinit var bitmapBuffer: Bitmap
    private lateinit var cameraExecutor: ExecutorService
    private var preview: Preview? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var modelTitle: String = ""

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Permission not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this@DetectionActivity,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS,
            )
        }

        objectDetectorUtils = ObjectDetectorUtils(
            context = this@DetectionActivity,
            objectDetectorListener = this
        )

        if (intent.extras != null) {
            val model = intent.getStringExtra(MODEL)!!
            modelTitle = model
            val currentModelUsed = when (model) {
                "Pepaya" -> MODEL_PAPAYA
                "Buah Naga" -> MODEL_DRAGON_FRUIT
                "Mangga" -> MODEL_MANGO
                "Tomat" -> MODEL_TOMATO
                else -> MODEL_PAPAYA
            }
            Log.d("currentModel", currentModelUsed)
            objectDetectorUtils.clearObjectDetector()
            objectDetectorUtils.currentModel = currentModelUsed

        }

        // init background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        binding.viewFinder.post {
            setUpCamera()
        }

        binding.toolbar.apply {
            setNavigationOnClickListener {
                finish()
            }
            title = getString(R.string.scan_your_s, modelTitle)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this@DetectionActivity)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases() {
        val cameraProvider =
            cameraProvider ?: throw IllegalStateException("Camera initialization failed")

        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(AspectRatio.RATIO_4_3)
            .setTargetRotation(binding.viewFinder.display.rotation)
            .build()

        imageAnalyzer =
            ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(binding.viewFinder.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { image ->
                        if (!::bitmapBuffer.isInitialized) {
                            bitmapBuffer = Bitmap.createBitmap(
                                image.width,
                                image.height,
                                Bitmap.Config.ARGB_8888
                            )
                        }
                        detectorObjects(image)
                    }
                }

        cameraProvider.unbindAll()

        try {
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)

            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Log.e("ObjectDetection", "Use Case binding failed", exc)
        }
    }

    private fun detectorObjects(image: ImageProxy) {
        // Copy out RGB bits to the shared bitmap buffer
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }

        val imageRotation = image.imageInfo.rotationDegrees
        // Pass Bitmap and rotation to the object detector helper for processing and detection
        objectDetectorUtils.detect(bitmapBuffer, imageRotation)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        imageAnalyzer?.targetRotation = binding.viewFinder.display.rotation
    }

    override fun onError(error: String) {
        runOnUiThread {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResults(
        results: MutableList<Detection>?,
        inferenceTime: Long,
        imageHeight: Int,
        imageWidth: Int
    ) {
        runOnUiThread {
            binding.overlay.setResults(
                results ?: LinkedList<Detection>(),
                imageHeight,
                imageWidth,
            )

            binding.overlay.invalidate()
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}