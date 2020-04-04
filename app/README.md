# Airship Push Notification Kotlin

### Introduction:

This project is created in the intention to understand QR and Barcode Scanner and make it as
readily usable component to integrate it with any projects

----------------------------------------------------------------------------------------------------

### Installation:

Step 1: Add the maven repository to build.gradle

Inside allprojects -> repositories

    ``` 
    maven(){
                url "https://maven.google.com"
            }
    ```

Step 2: Apply the vision plugin to the top of the application level build.gradle file.

    ```
    implementation 'com.google.android.gms:play-services-vision:19.0.0'
    ```

----------------------------------------------------------------------------------------------------

### Configuration:

1. Add the below in AndroidManifest file

```
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera.autofocus" />

```

```
<meta-data
            android:name="com.google.android.gms.vision.DEPENDENCIES"
            android:value="barcode" />
```

----------------------------------------------------------------------------------------------------

### Coding Part:

1. Get Camera permission from the user

```
 if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.CAMERA),1)
        }else{
            Toast.makeText(applicationContext,"Camera Permission granted already..",Toast.LENGTH_SHORT).show()
        }
```
    	        
2. Initialize surfaceview, barcodeDetector and cameraSource. Set surface callback for cameraview

3. Inside surfaceCreated override method add the below code to start the camera.

```
cameraSource.start(surfaceView.holder)
```

4. set barcodeDetector setProcessor, setProcessot implements 2 override methods.

    a. release()
    b. receiveDetections()

5. in the receiveDetections() method add the below code to send the detected value 

```
    var barcodes: SparseArray<Barcode> = detections!!.detectedItems
    if (barcodes.size() != 0) {
    var intent: Intent = Intent(applicationContext, MainActivity::class.java)
    intent.putExtra("barcodes", barcodes.valueAt(0))
    setResult(Activity.RESULT_OK, intent)
    finish()
```

6. In MainActivity inside onActivityResult() add the below code to show the detected value.

    ```
        if(requestCode == 0 && resultCode == Activity.RESULT_OK){
            if(data!=null){
                var barcode:Barcode = data.getParcelableExtra("barcodes")
                scannedText.post(object: Runnable {
                    override fun run() {
                        scannedText.setText(barcode.displayValue)
                    }

                })
            }
        }
    ```
    