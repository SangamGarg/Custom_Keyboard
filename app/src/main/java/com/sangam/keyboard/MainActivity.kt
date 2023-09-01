package com.sangam.keyboard

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                captureScreenshot()
            }, 5000)
        }
    }

    private fun captureScreenshot() {
        try {
            // Define the directory where you want to save the screenshot
            val picturesDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)

            // Create a screenshot file within the "Pictures" directory
            val screenshotFile = File(picturesDirectory, "screenshot_${Random.nextInt(100000)}.png")

            // Create a bitmap of the root view
            val rootView = window.decorView.rootView
            rootView.isDrawingCacheEnabled = true
            val bitmap = Bitmap.createBitmap(rootView.drawingCache)

            // Save the bitmap as a PNG file in the "Pictures" directory
            val outputStream = FileOutputStream(screenshotFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Inform the user that the screenshot was saved
            Toast.makeText(
                this,
                "Screenshot saved as ${screenshotFile.absolutePath}",
                Toast.LENGTH_LONG
            ).show()
            Log.d("SCREENSHOT", screenshotFile.absolutePath)
        } catch (e: Exception) {
            Toast.makeText(this, "Error capturing screenshot", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

}