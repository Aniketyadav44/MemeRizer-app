package com.asyprod.memerizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var mAdapter: MemeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerV)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val refBtn = findViewById<CardView>(R.id.refresh)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetch()
        mAdapter = MemeAdapter(this)
        recyclerView.adapter = mAdapter

        refBtn.setOnClickListener { fetch() }
    }
      private  fun fetch() {
            progressBar.visibility = View.VISIBLE
            val queue = Volley.newRequestQueue(this)
            val url = "https://meme-api.herokuapp.com/gimme/20"
            val jsonObjRequest = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener {
                    val listArray: ArrayList<Meme> = ArrayList()
                    val jsonArray = it.getJSONArray("memes")
                    for (i in 0 until jsonArray.length()) {
                        val memeObj = jsonArray.getJSONObject(i)
                        val meme = Meme(
                            memeObj.getString("url"),
                            memeObj.getString("title"),
                            memeObj.getString("author"),
                            memeObj.getString("ups")
                        )
                        listArray.add(meme)
                    }
                    mAdapter.refresh(listArray)
                    progressBar.visibility = View.INVISIBLE
                },
                Response.ErrorListener {
                    Toast.makeText(this, "An Error ocurred!,please try again later.", Toast.LENGTH_SHORT)
                    progressBar.visibility = View.INVISIBLE
                })
            queue.add(jsonObjRequest)
        }
}