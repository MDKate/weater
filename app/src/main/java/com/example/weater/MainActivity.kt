package com.example.weater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editText1 = findViewById<EditText>(R.id.editText1)
        var but1 = findViewById<Button>(R.id.but1)
        var result = findViewById<TextView>(R.id.result)

        but1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (editText1.text.toString().trim().equals(""))
                    Toast.makeText(this@MainActivity, R.string.no_input, Toast.LENGTH_SHORT).show()
                else {
                    var city1: String = editText1.getText().toString()
                    var key1: String = "a6b8a9bf88133f80b4f115fe3f525f4b"
                    var URLad: String =
                        "http://api.openweathermap.org/data/2.5/weather?q=" + city1 + "&appid=" + key1 + "&units=metric&lang=ru"
                    val пetURLData = GetURLData(URLad, result)
                    пetURLData.execute()

                }
            }
        }
        )
    }

    class GetURLData(val URLad: String,  var _Result: TextView) : AsyncTask<String, String, String>() {


//    constructor(var uRLad: String) {
//        URLad = uRLad
//    }

        fun onPreExecute(result: TextView) {
            super.onPreExecute()
            result.setText("Ожидайте...")
        }


        override fun doInBackground(vararg params: String?): String {

            var line: String = "000"
            var connect = URL(URLad).openConnection() as HttpURLConnection
            connect.inputStream.bufferedReader().use { reader ->
                line = reader.readLine()
                var jsonObject = JSONObject(line)
                var lineMain = jsonObject.get("main") as JSONObject
                var lineTemp = lineMain.get("temp").toString()
//                var lineArr = lineMain.get("weather") as JSONArray
                line = lineTemp
                _Result.setText(line)

            }

            return line
        }


    }
}