package com.BELAIBET.simulation.web

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
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
import com.BELAIBET.simulation.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class WebActivity : AppCompatActivity() {

    private val cookie: CookieManager by lazy { CookieManager.getInstance() }
    private lateinit var sPref : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var webSite: WebView? = null
    private var pathFileCall: ValueCallback<Array<Uri>>? = null
    private var pathCamera: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_activity)
        webSite = findViewById(R.id.website)
        sPref = applicationContext.getSharedPreferences("MyPref", 0)
        editor = sPref.edit()
        makeSettings(webSite!!.settings)
        CookieManager.getInstance().setAcceptCookie(true)
        cookie.setAcceptCookie(true)
        cookie.setAcceptThirdPartyCookies(webSite, true);

        webSite!!.webViewClient = CheckClient()
        webSite!!.webChromeClient = ClientChrome()

        if (savedInstanceState != null)
            webSite?.restoreState(savedInstanceState)
        else sPref.getString("url",null)?.let { webSite?.loadUrl(it) }
//        else webSite?.loadUrl("www.pin-up664.com")
    }

    private fun makeSettings(settings: WebSettings){
        settings.domStorageEnabled = true
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.databaseEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT

    }
    override fun onSaveInstanceState(outState: Bundle) {
        webSite?.saveState(outState)
        pathCamera=null
        super.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        if (webSite!!.canGoBack()) {
            webSite!!.goBack()
        } else {

        }
    }

    private fun checkAllPermission(permissions: Array<String>): Boolean {
        var accessPermision = true
        permissions.forEach {
            if (checkAllPermission(it).not())
                accessPermision = false
        }
        return accessPermision
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

        override fun onShowFileChooser(
            view: WebView,
            filePath: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {

            if (pathFileCall != null) {
                pathFileCall!!.onReceiveValue(null)
            }
            pathFileCall = filePath
            var intentPicture: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intentPicture!!.resolveActivity(this@WebActivity.packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = imgFileCreator()
                    intentPicture.putExtra("PhotoPath", pathCamera)
                } catch (ex: IOException) {

                }

                // Continue only if the File was successfully created
                if (photoFile != null) {
                    pathCamera = "file:" + photoFile.absolutePath
                    intentPicture.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile)
                    )
                } else {
                    intentPicture = null
                }
            }
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = "image/*"
            val arrayOfIntents: Array<Intent?> = intentPicture?.let { arrayOf(it) } ?: arrayOfNulls(0)
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "ChooseImage")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOfIntents)
            startActivityForResult(chooserIntent, REQUEST_FOR_INPUT)

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

        override fun onPermissionRequest(request: PermissionRequest) {
            val launchPermission = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                if (isGranted) {
                    request.grant(request.resources)
                } else {

                }
            }
            launchPermission.launch(android.Manifest.permission.CAMERA)
        }


    }

    @Throws(IOException::class)
    private fun imgFileCreator(): File {
        val stamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "JPEG_" + stamp + "_"
        val stor = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            fileName,
            ".jpg",
            stor
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (requestCode != REQUEST_FOR_INPUT || pathFileCall == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri>? = null
            if (resultCode == ComponentActivity.RESULT_OK) {
                if (data == null) {
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
            Log.e("chooser", e.toString())
        }
    }

    companion object {

        private const val REQUEST_FOR_INPUT = 1
    }

}