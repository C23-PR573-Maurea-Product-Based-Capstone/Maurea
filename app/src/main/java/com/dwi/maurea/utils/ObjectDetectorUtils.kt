package com.dwi.maurea.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.Rot90Op
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector

class ObjectDetectorUtils(
    var threshold: Float = 0.5f,
    var numThreads: Int = 2,
    var maxResults: Int = 3,
    var currentModel: String = MODEL_PAPAYA,
    val context: Context,
    val objectDetectorListener: DetectorListener?
) {
    private var objectDetector: ObjectDetector? = null

    init {
        setupObjectDetector()
    }

    fun clearObjectDetector() {
        objectDetector = null
    }

    private fun setupObjectDetector() {
        val optionsBuilder = ObjectDetector.ObjectDetectorOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)

        val baseOptionBuilder = BaseOptions.builder().setNumThreads(numThreads)

        optionsBuilder.setBaseOptions(baseOptionBuilder.build())

        val modelName =
            when (currentModel) {
                MODEL_PAPAYA -> "papaya.tflite"
                MODEL_DRAGON_FRUIT -> "dragon_fruit.tflite"
                MODEL_WATERMELON -> "watermelon.tflite"
                MODEL_TOMATO -> "tomato.tflite"
                else -> "papaya.tflite"
            }

        try {
            objectDetector = ObjectDetector.createFromFileAndOptions(context, modelName, optionsBuilder.build())
            Log.d("ObjectDetectorUtils", "model used: $modelName")
        } catch (e: IllegalStateException) {
            objectDetectorListener?.onError(e.message.toString())
            Log.d("ObjectDetectorUtils", e.message.toString())
        }
    }

    fun detect(image: Bitmap, imageRotation: Int) {
        if (objectDetector == null) {
            setupObjectDetector()
        }

        var inferenceTime = SystemClock.uptimeMillis()


        val imageProcessor = ImageProcessor.Builder()
            .add(Rot90Op(-imageRotation / 90))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))

        val results = objectDetector?.detect(tensorImage)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime

        objectDetectorListener?.onResults(
            results,
            inferenceTime,
            tensorImage.height,
            tensorImage.width,
        )

    }

    interface DetectorListener {
        fun onError(error: String)
        fun onResults(
            results: MutableList<Detection>?,
            inferenceTime: Long,
            imageHeight: Int,
            imageWidth: Int
        )
    }

    companion object {
        const val MODEL_PAPAYA = "papaya"
        const val MODEL_DRAGON_FRUIT = "dragon_fruit"
        const val MODEL_WATERMELON = "watermelon"
        const val MODEL_TOMATO = "tomato"
    }

}