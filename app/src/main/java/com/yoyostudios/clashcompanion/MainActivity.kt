package com.yoyostudios.clashcompanion

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityManager
import com.yoyostudios.clashcompanion.accessibility.ClashCompanionAccessibilityService
import com.yoyostudios.clashcompanion.capture.ScreenCaptureService
import com.yoyostudios.clashcompanion.overlay.OverlayManager
import com.yoyostudios.clashcompanion.speech.SpeechService
import com.yoyostudios.clashcompanion.util.Coordinates

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private var overlayManager: OverlayManager? = null
    private lateinit var mediaProjectionLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val REQUEST_MIC_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Coordinates.init(this)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        val btnOverlayPerm = findViewById<Button>(R.id.btnOverlayPermission)
        val btnAccessibility = findViewById<Button>(R.id.btnAccessibility)
        val btnMic = findViewById<Button>(R.id.btnMicPermission)
        val btnCapture = findViewById<Button>(R.id.btnCapture)
        val btnSpeech = findViewById<Button>(R.id.btnSpeech)
        val btnStartOverlay = findViewById<Button>(R.id.btnStartOverlay)

        // Request mic permission on launch if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_MIC_PERMISSION
            )
        }

        // Register MediaProjection result handler
        mediaProjectionLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val serviceIntent = Intent(this, ScreenCaptureService::class.java).apply {
                    putExtra(ScreenCaptureService.EXTRA_RESULT_CODE, result.resultCode)
                    putExtra(ScreenCaptureService.EXTRA_DATA, result.data)
                }
                startForegroundService(serviceIntent)
                updateStatus("Screen capture started")
            } else {
                updateStatus("Screen capture permission denied")
            }
        }

        btnOverlayPerm.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivity(intent)
            } else {
                updateStatus("Overlay permission already granted")
            }
        }

        btnAccessibility.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }

        btnMic.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED
            ) {
                updateStatus("Microphone permission already granted")
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_MIC_PERMISSION
                )
            }
        }

        btnCapture.setOnClickListener {
            if (ScreenCaptureService.instance != null) {
                updateStatus("Screen capture already running")
                return@setOnClickListener
            }
            val projectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            mediaProjectionLauncher.launch(projectionManager.createScreenCaptureIntent())
        }

        btnSpeech.setOnClickListener {
            if (SpeechService.instance != null) {
                updateStatus("Speech service already running")
                return@setOnClickListener
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED
            ) {
                updateStatus("ERROR: Grant microphone permission first")
                return@setOnClickListener
            }
            val intent = Intent(this, SpeechService::class.java)
            startForegroundService(intent)
            updateStatus("Speech service starting (loading models...)")

            // Poll until model is loaded, refresh status
            val pollHandler = android.os.Handler(mainLooper)
            val pollRunnable = object : Runnable {
                override fun run() {
                    val svc = SpeechService.instance
                    if (svc != null && svc.isReady()) {
                        refreshStatus()
                    } else {
                        pollHandler.postDelayed(this, 500)
                    }
                }
            }
            pollHandler.postDelayed(pollRunnable, 500)
        }

        btnStartOverlay.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                updateStatus("ERROR: Grant overlay permission first")
                return@setOnClickListener
            }
            if (!isAccessibilityServiceEnabled()) {
                updateStatus("ERROR: Enable Accessibility Service first")
                return@setOnClickListener
            }
            if (overlayManager == null) {
                overlayManager = OverlayManager(applicationContext)
            }
            overlayManager?.show()
            updateStatus("Overlay started — switch to Clash Royale")
        }
    }

    override fun onResume() {
        super.onResume()
        refreshStatus()
    }

    private fun refreshStatus() {
        val overlayOk = Settings.canDrawOverlays(this)
        val accessibilityOk = isAccessibilityServiceEnabled()
        val micOk = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        val captureOk = ScreenCaptureService.instance != null
        val speechRunning = SpeechService.instance != null
        val speechReady = SpeechService.instance?.isReady() == true

        val sb = StringBuilder()
        sb.appendLine("── Clash Companion Status ──")
        sb.appendLine()
        sb.appendLine("Overlay Permission: ${if (overlayOk) "GRANTED" else "NOT GRANTED"}")
        sb.appendLine("Accessibility: ${if (accessibilityOk) "ENABLED" else "NOT ENABLED"}")
        sb.appendLine("Microphone: ${if (micOk) "GRANTED" else "NOT GRANTED"}")
        sb.appendLine("Screen Capture: ${if (captureOk) "RUNNING" else "NOT STARTED"}")
        sb.appendLine("Speech Service: ${if (speechReady) "READY" else if (speechRunning) "LOADING..." else "NOT STARTED"}")
        sb.appendLine()
        if (overlayOk && accessibilityOk && micOk) {
            sb.appendLine("Ready! Start services, then overlay.")
        } else {
            sb.appendLine("Complete the setup steps above.")
        }

        statusText.text = sb.toString()
    }

    private fun updateStatus(message: String) {
        statusText.text = message
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val enabledServices = android.provider.Settings.Secure.getString(
            contentResolver, "enabled_accessibility_services"
        ) ?: return false
        return enabledServices.contains(packageName)
    }

    override fun onDestroy() {
        super.onDestroy()
        overlayManager?.hide()
    }
}
