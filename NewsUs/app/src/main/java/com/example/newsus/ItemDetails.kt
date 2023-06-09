package com.example.newsus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.newsus.databinding.ActivityItemDetailsBinding
import com.example.newsus.savedItems.sDb
import com.example.newsus.savedItems.sNews

class ItemDetails : AppCompatActivity() {
    lateinit var binding: ActivityItemDetailsBinding
    lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bundle = intent.extras as Bundle
        createPage()

        supportActionBar?.title = "News Details"

        val db = sDb.getNewsDb(this)
        var saveBtn = binding.saveImgBtn
        saveBtn.setOnClickListener{
            val tempSaveNews = sNews(
                null,
                bundle!!.getString("author") as String,
                bundle!!.getString("content") as String,
                bundle!!.getString("description") as String,
                bundle!!.getString("title") as String,
                bundle!!.getString("imgUrl") as String,
            )

            db.getNewsDao().getAllSavedNews().asLiveData().observe(this){
                if(it.size == 0){
                    Thread{
                        db.getNewsDao().insertNews(tempSaveNews)
                    }.start()
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                }
                else{
                    for (i in it){
                        if(i.urlToImage == bundle!!.getString("imgUrl") as String){
                            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                            break
                        }
                        if(i == it[it.size-1]){
                            Thread{
                                db.getNewsDao().insertNews(tempSaveNews)
                            }.start()
                            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun createPage() {
        binding.titleDet.text = bundle!!.getString("title")
        binding.discriptionDet.text = bundle!!.getString("description")
        binding.authorDet.text = "Author: ${bundle!!.getString("author")}"
        binding.content.text = bundle!!.getString("content")

        val img = binding.imageView3
        val url = bundle!!.getString("imgUrl")
        Glide.with(img)
            .load(url)
            .placeholder(R.drawable.image)
            .error(R.drawable.image)
            .fallback(R.drawable.image)
            .into(img)
    }

}