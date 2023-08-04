package com.BELAIBET.simulation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.webkit.CookieManager
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class WebActivity : AppCompatActivity() {

    private val cookie: CookieManager by lazy { CookieManager.getInstance() }
    private var sPref = applicationContext.getSharedPreferences("MyPref", 0)
    private val editor = sPref!!.edit()
    private var webLayout: WebView? = null
    private var captureImg: Uri? = null
    private var pathFileCall: ValueCallback<Array<Uri>>? = null
    private var pathCamera: String? = null
    private var messageForUpload: ValueCallback<Uri?>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_activity)
        webLayout = findViewById(R.id.website)

        webLayout!!.settings.domStorageEnabled = true
        webLayout!!.settings.javaScriptEnabled = true
        webLayout!!.settings.useWideViewPort = true
        webLayout!!.settings.databaseEnabled = true
        webLayout!!.settings.javaScriptCanOpenWindowsAutomatically = true
        webLayout!!.settings.cacheMode = WebSettings.LOAD_DEFAULT

        CookieManager.getInstance().setAcceptCookie(true)

        cookie.setAcceptCookie(true)
        cookie.setAcceptThirdPartyCookies(webLayout, true);

        webLayout!!.webViewClient = CheckClient()
        webLayout!!.webChromeClient = ClientChrome()
//        modelProvider.checkLink()

        if (savedInstanceState != null)
            webLayout?.restoreState(savedInstanceState)
        else sPref.getString("url",null)?.let { webLayout?.loadUrl(it) }
//        else webLayout?.loadUrl("www.pin-up664.com")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webLayout?.saveState(outState)
        super.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        if (webLayout!!.canGoBack()) {
            webLayout!!.goBack()
        } else {

        }
    }

    private fun checkAllPermission(permissions: Array<String>): Boolean {
        var permissionTrue = true
        permissions.forEach {
            if (checkAllPermission(it).not())
                permissionTrue = false
        }
        return permissionTrue
    }

    private fun checkAllPermission(permission: String): Boolean =
        ContextCompat.checkSelfPermission(
            this, permission
        ) == PackageManager.PERMISSION_GRANTED

    inner class CheckClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request != null) {
                view?.loadUrl(request.url.toString())
            }
            return false
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            view.loadUrl(url!!)
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            cookie.setCookie(url, sPref.getString("cookie",null))
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            CookieManager.getInstance().flush()
            editor.putString("cookie",cookie.getCookie(url))
        }
    }

    inner class ClientChrome : WebChromeClient() {

        // For Android 5.0
        override fun onShowFileChooser(
            view: WebView,
            filePath: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {

            // Double check that we don't have any existing callbacks
            if (pathFileCall != null) {
                pathFileCall!!.onReceiveValue(null)
            }
            pathFileCall = filePath
            var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent!!.resolveActivity(this@WebActivity.packageManager) != null) {
                // Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = imgFileCreator()
                    takePictureIntent.putExtra("PhotoPath", pathCamera)
                } catch (ex: IOException) {

                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    pathCamera = "file:" + photoFile.absolutePath
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile)
                    )
                } else {
                    takePictureIntent = null
                }
            }
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = "image/*"
            val intentArray: Array<Intent?>
            intentArray = takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "ChooseImage")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (!checkAllPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    ActivityCompat.requestPermissions(
                        (this@WebActivity as Activity?)!!,
                        arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                        41
                    )
                    return true
                }
            }

            if (!checkAllPermission(arrayOf(Manifest.permission.CAMERA))) {
                ActivityCompat.requestPermissions(
                    (this@WebActivity as Activity?)!!,
                    arrayOf<String>(Manifest.permission.CAMERA),
                    40
                )
            }
            return true

        }

        // openFileChooser for Android 3.0+
        // openFileChooser for Android < 3.0
        @JvmOverloads
        fun openFileChooser(uploadMsg: ValueCallback<Uri?>?, acceptType: String? = "") {

            messageForUpload = uploadMsg
            // Create AndroidExampleFolder at sdcard
            // Create AndroidExampleFolder at sdcard
            val imageStorageDir = File(
                Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                ), "AndroidExampleFolder"
            )
            if (!imageStorageDir.exists()) {
                // Create AndroidExampleFolder at sdcard
                imageStorageDir.mkdirs()
            }

            // Create camera captured image file path and name
            val file = File(
                imageStorageDir.toString() + File.separator + "IMG_"
                        + System.currentTimeMillis().toString() + ".jpg"
            )
            captureImg = Uri.fromFile(file)

            // Camera capture image intent
            val captureIntent = Intent(
                MediaStore.ACTION_IMAGE_CAPTURE
            )
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, captureImg)
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.addCategory(Intent.CATEGORY_OPENABLE)
            i.type = "image/*"

            // Create file chooser intent
            val chooserIntent = Intent.createChooser(i, "ChooseImage")

            // Set camera intent to file chooser
            chooserIntent.putExtra(
                Intent.EXTRA_INITIAL_INTENTS, arrayOf<Parcelable>(captureIntent)
            )

            // On select image call onActivityResult method of activity
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE)
        }

        //openFileChooser for other Android versions
        fun openFileChooser(
            uploadMsg: ValueCallback<Uri?>?,
            acceptType: String?,
            capture: String?
        ) {
            openFileChooser(uploadMsg, acceptType)
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            val permissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    request.grant(request.resources)
                } else {
                    // Do otherwise
                }
            }
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }


    }

    @Throws(IOException::class)
    private fun imgFileCreator(): File {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",  /* suffix */
            storageDir /* directory */
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode != INPUT_FILE_REQUEST_CODE || pathFileCall == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri>? = null
            if (resultCode == ComponentActivity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (pathCamera != null) {
                        results = arrayOf(Uri.parse(pathCamera))
                    }
                } else {
                    val dataString = data.dataString
                    if (dataString != null) {
                        results = arrayOf(Uri.parse(dataString))
                    }
                }
            }
            pathFileCall!!.onReceiveValue(results)
            pathFileCall = null
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("scanner", e.toString())
        }
    }

    companion object {
        private const val INPUT_FILE_REQUEST_CODE = 1
        private const val FILECHOOSER_RESULTCODE = 1
    }

}