package com.example.qrbarcodescanner

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.lang.Exception


class ScanBarCode : AppCompatActivity() {

    lateinit var surfaceView: SurfaceView
    lateinit var barcodeDetector: BarcodeDetector
    lateinit var cameraSource: CameraSource

    //region onCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_bar_code)
        initializeValue()
        surfaceCallback()
        barcodeScan()
    }
    //endregion

    //region initializeValue
    private fun initializeValue() {
            surfaceView = findViewById(R.id.surfaceView)

            barcodeDetector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS).build()

            cameraSource = CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1024)
                .setAutoFocusEnabled(true)
                .build()
    }
    //endregion

    //region surfaceCallback for cameraview
    private fun surfaceCallback() {
            surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    try {
                        cameraSource.start(surfaceView.holder)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                }
            })
    }
    //endregion

    //region barcodeScan for detect barcode
    private fun barcodeScan() {
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                try {
                    var barcodes: SparseArray<Barcode> = detections!!.detectedItems
                    if (barcodes.size() != 0) {
                        var intent: Intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("barcodes", barcodes.valueAt(0))
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
    //endregion
}
