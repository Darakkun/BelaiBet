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


class WebActivityBelai : AppCompatActivity() {

    private val cookieSlotsBelaiBet: CookieManager by lazy { CookieManager.getInstance() }
    private lateinit var sPrefBelai : SharedPreferences
    private lateinit var editorPrefBelai: SharedPreferences.Editor
    private var websiteBelaibet: WebView? = null
    private var pathFileCallBelai: ValueCallback<Array<Uri>>? = null
    private var pathCamerabelai: String? = null


    override fun onCreate(savedState_ban: Bundle?) {
        super.onCreate(savedState_ban)
        setContentView(R.layout.online_activity_belai_layout)
        websiteBelaibet = findViewById(R.id.website)
        sPrefBelai = applicationContext.getSharedPreferences("MyPref_belaibet", 0)
        editorPrefBelai = sPrefBelai.edit()
        makeSettings(websiteBelaibet!!.settings)
        CookieManager.getInstance().setAcceptCookie(true)
        cookieSlotsBelaiBet.setAcceptCookie(true)
        cookieSlotsBelaiBet.setAcceptThirdPartyCookies(websiteBelaibet, true);

        websiteBelaibet!!.webViewClient = CheckClient()
        websiteBelaibet!!.webChromeClient = ClientChrome()

        if (savedState_ban != null)
            websiteBelaibet?.restoreState(savedState_ban)
        else sPrefBelai.getString("url_slots",null)?.let { websiteBelaibet?.loadUrl(it) }
//        else websiteBelaibet?.loadUrl("www.pin-up664.com")
    }

    private fun makeSettings(settingsBelai: WebSettings){
        settingsBelai.domStorageEnabled = true
        settingsBelai.javaScriptEnabled = true
        settingsBelai.useWideViewPort = true
        settingsBelai.databaseEnabled = true
        settingsBelai.javaScriptCanOpenWindowsAutomatically = true
        settingsBelai.cacheMode = WebSettings.LOAD_DEFAULT

    }
    override fun onSaveInstanceState(outStateBelai: Bundle) {
        websiteBelaibet?.saveState(outStateBelai)
        pathCamerabelai=null
        super.onSaveInstanceState(outStateBelai)
    }


    override fun onBackPressed() {
        if (websiteBelaibet!!.canGoBack()) {
            websiteBelaibet!!.goBack()
        } else {

        }
    }

    private fun checkAllPermission(listPermissionsBelai: Array<String>): Boolean {
        var accessToPermision = true
        listPermissionsBelai.forEach {
            if (checkAllPermission(it).not())
                accessToPermision = false
        }
        return accessToPermision
    }

    private fun checkAllPermission(permissionSingularBelai: String): Boolean =
        ContextCompat.checkSelfPermission(
            this, permissionSingularBelai
        ) == PackageManager.PERMISSION_GRANTED

    inner class CheckClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view_belaibet: WebView?,
            request_belaibet: WebResourceRequest?
        ): Boolean {
            return try {
                if (request_belaibet != null) {
                    view_belaibet?.loadUrl(request_belaibet.url.toString())
                }
                return false
            } catch (extraBelai:Exception){
                false
            }
        }

        override fun shouldOverrideUrlLoading(view_belaibet: WebView, url_belaibet: String?): Boolean {
            return try {
                view_belaibet.loadUrl(url_belaibet!!)
                false
            } catch (extraBelai:Exception){
                false
            }

        }

        override fun onPageStarted(view_belaibet: WebView?, url_belaibet: String?, favicon_belaibet: Bitmap?) {
            super.onPageStarted(view_belaibet, url_belaibet, favicon_belaibet)
            cookieSlotsBelaiBet.setCookie(url_belaibet, sPrefBelai.getString("cookieSlotsBelaiBet",null))
        }

        override fun onPageFinished(view_belaibet: WebView?, url_belaibet: String?) {
            super.onPageFinished(view_belaibet, url_belaibet)
            CookieManager.getInstance().flush()
            editorPrefBelai.putString("cookieSlotsBelaiBet",cookieSlotsBelaiBet.getCookie(url_belaibet))
        }
    }

    inner class ClientChrome : WebChromeClient() {

        override fun onShowFileChooser(
            view_web_belai: WebView,
            filePath_belai: ValueCallback<Array<Uri>>,
            fileChooserParams_belaibet: FileChooserParams
        ): Boolean {

            if (pathFileCallBelai != null) {
                pathFileCallBelai!!.onReceiveValue(null)
            }
            pathFileCallBelai = filePath_belai
            var intentPicture: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intentPicture!!.resolveActivity(this@WebActivityBelai.packageManager) != null) {
                var fileForPhotos: File? = null
                try {
                    fileForPhotos = imgFileCreator()
                    intentPicture.putExtra("PhotoPath", pathCamerabelai)
                } catch (extraBelai: IOException) {

                }

                // Continue only if the File was successfully created
                if (fileForPhotos != null) {
                    pathCamerabelai = "file:" + fileForPhotos.absolutePath
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
            startActivityForResult(choiceIntent, REQUEST_FOR_INPUT_BELAI)

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (!checkAllPermission(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))) {
                    ActivityCompat.requestPermissions(
                        (this@WebActivityBelai as Activity?)!!,
                        arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE),
                        41
                    )
                    return true
                }
            }

            if (!checkAllPermission(arrayOf(Manifest.permission.CAMERA))) {
                ActivityCompat.requestPermissions(
                    (this@WebActivityBelai as Activity?)!!,
                    arrayOf<String>(Manifest.permission.CAMERA),
                    40
                )
            }
            return true

        }
        private var mPermissionRequestBelai: PermissionRequest? = null
        override fun onPermissionRequest(request_belaibet: PermissionRequest) {
            Log.i("tag", "onPermissionRequest")
            mPermissionRequestBelai = request_belaibet
            val requestedResources = request_belaibet.resources
            for (r in requestedResources) {
                if (r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                    val alertDialogBuilder: AlertDialog.Builder =  AlertDialog.Builder(this@WebActivityBelai)
                        .setTitle("Allow Permission to camera")
                        .setPositiveButton("Allow",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                                mPermissionRequestBelai!!.grant(arrayOf<String>(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                                Log.d("tagBelai", "GrantedBelai")
                            })
                        .setNegativeButton("Deny",
                            DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                                mPermissionRequestBelai!!.deny()
                                Log.d("tagBelai", "DeniedBelai")
                            })
                    val alertDialog: AlertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                    break
                }
            }
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

    public override fun onActivityResult(requestCode_belaibet: Int, resultCode_belaibet: Int, data_belaibet: Intent?) {
        try {
            if (requestCode_belaibet != REQUEST_FOR_INPUT_BELAI || pathFileCallBelai == null) {
                super.onActivityResult(requestCode_belaibet, resultCode_belaibet, data_belaibet)
                return
            }
            var resultsBelaibet: Array<Uri>? = null
            if (resultCode_belaibet == ComponentActivity.RESULT_OK) {
                if (data_belaibet == null) {
                    if (pathCamerabelai != null) {
                        resultsBelaibet = arrayOf(Uri.parse(pathCamerabelai))
                    }
                } else {
                    val datastringBelaibet = data_belaibet.dataString
                    if (datastringBelaibet != null) {
                        resultsBelaibet = arrayOf(Uri.parse(datastringBelaibet))
                    }
                }
            }
            pathFileCallBelai!!.onReceiveValue(resultsBelaibet)
            pathFileCallBelai = null
        } catch (extraBelai: java.lang.Exception) {
            Toast.makeText(this, extraBelai.toString(), Toast.LENGTH_SHORT).show();
            Log.e("chooser_text", extraBelai.toString())
        }
    }

    companion object {

        private const val REQUEST_FOR_INPUT_BELAI = 1
    }

}