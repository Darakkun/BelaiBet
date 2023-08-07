package com.BELAIBET.simulation.web

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.BELAIBET.simulation.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class WebActivity : AppCompatActivity() {

    private val cookie_slots: CookieManager by lazy { CookieManager.getInstance() }
    private lateinit var sPref : SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var webSite_belaiBet: WebView? = null
    private var pathFileCall: ValueCallback<Array<Uri>>? = null
    private var pathCamera: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.online_activity)
        webSite_belaiBet = findViewById(R.id.website)
        sPref = applicationContext.getSharedPreferences("MyPref_belaibet", 0)
        editor = sPref.edit()
        makeSettings(webSite_belaiBet!!.settings)
        CookieManager.getInstance().setAcceptCookie(true)
        cookie_slots.setAcceptCookie(true)
        cookie_slots.setAcceptThirdPartyCookies(webSite_belaiBet, true);

        webSite_belaiBet!!.webViewClient = CheckClient()
        webSite_belaiBet!!.webChromeClient = ClientChrome()

        if (savedInstanceState != null)
            webSite_belaiBet?.restoreState(savedInstanceState)
        else sPref.getString("url_slots",null)?.let { webSite_belaiBet?.loadUrl(it) }
//        else webSite_belaiBet?.loadUrl("www.pin-up664.com")
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
        webSite_belaiBet?.saveState(outState)
        pathCamera=null
        super.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        if (webSite_belaiBet!!.canGoBack()) {
            webSite_belaiBet!!.goBack()
        } else {

        }
    }

    private fun checkAllPermission(listPermissions: Array<String>): Boolean {
        var accessToPermision = true
        listPermissions.forEach {
            if (checkAllPermission(it).not())
                accessToPermision = false
        }
        return accessToPermision
    }

    private fun checkAllPermission(permissionSingular: String): Boolean =
        ContextCompat.checkSelfPermission(
            this, permissionSingular
        ) == PackageManager.PERMISSION_GRANTED

    inner class CheckClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            return try {
                if (request != null) {
                    view?.loadUrl(request.url.toString())
                }
                return false
            } catch (e:Exception){
                false
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
            return try {
                view.loadUrl(url!!)
                false
            } catch (e:Exception){
                false
            }

        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            cookie_slots.setCookie(url, sPref.getString("cookie_slots",null))
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            CookieManager.getInstance().flush()
            editor.putString("cookie_slots",cookie_slots.getCookie(url))
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
                var fileForPhotos: File? = null
                try {
                    fileForPhotos = imgFileCreator()
                    intentPicture.putExtra("PhotoPath", pathCamera)
                } catch (ex: IOException) {

                }

                // Continue only if the File was successfully created
                if (fileForPhotos != null) {
                    pathCamera = "file:" + fileForPhotos.absolutePath
                    intentPicture.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(fileForPhotos)
                    )
                } else {
                    intentPicture = null
                }
            }
            val intentForContentSelection = Intent(Intent.ACTION_GET_CONTENT)
            intentForContentSelection.addCategory(Intent.CATEGORY_OPENABLE)
            intentForContentSelection.type = "image/*"
            val arrayOfIntents: Array<Intent?> = intentPicture?.let { arrayOf(it) } ?: arrayOfNulls(0)
            val choiceIntent = Intent(Intent.ACTION_CHOOSER)
            choiceIntent.putExtra(Intent.EXTRA_INTENT, intentForContentSelection)
            choiceIntent.putExtra(Intent.EXTRA_TITLE, "ChooseImage")
            choiceIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOfIntents)
            startActivityForResult(choiceIntent, REQUEST_FOR_INPUT)

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
        private var mPermissionRequest: PermissionRequest? = null
        override fun onPermissionRequest(request: PermissionRequest) {
            Log.i("tag", "onPermissionRequest")
            mPermissionRequest = request
            val requestedResources = request.resources
            for (r in requestedResources) {
                if (r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                    // In this sample, we only accept video capture request.
                    val alertDialogBuilder: AlertDialog.Builder =  AlertDialog.Builder(this@WebActivity)
                        .setTitle("Allow Permission to camera")
                        .setPositiveButton("Allow",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                                mPermissionRequest!!.grant(arrayOf<String>(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                                Log.d("tag", "Granted")
                            })
                        .setNegativeButton("Deny",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                                mPermissionRequest!!.deny()
                                Log.d("tag", "Denied")
                            })
                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    break
                }
            }
        }

//        override fun onPermissionRequest(request: PermissionRequest) {
//            val launchPermission = registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { isGranted ->
//                if (isGranted) {
//                    request.grant(request.resources)
//                } else {
//
//                }
//            }
//            launchPermission.launch(android.Manifest.permission.CAMERA)
//        }


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
            Log.e("chooser_text", e.toString())
        }
    }

    companion object {

        private const val REQUEST_FOR_INPUT = 1
    }

}