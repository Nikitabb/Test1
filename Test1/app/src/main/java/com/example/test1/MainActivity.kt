package com.example.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    val URL = "https://api.icndb.com/jokes/random"
    val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButtonTextView.setOnClickListener { CodeAPI() }
    }
    private fun CodeAPI(){
    val request = Request.Builder().url(URL).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response?.body()?.string()
                val Text = (JSONObject(json).getJSONObject("value").get("joke")).toString()
                textview.text = Text

            }

        })
    }
}