package com.example.newsus.savedItems

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsus.R
import com.example.newsus.databinding.SavedNewsItemBinding

class sAdapter(var snewsList:List<sNews>, val context: Context) : RecyclerView.Adapter<sAdapter.sHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class sHolder(item: View , listener: onItemClickListener, context: Context): RecyclerView.ViewHolder(item){
        val binding = SavedNewsItemBinding.bind(item)
        var builder = AlertDialog.Builder(context)
        val text = binding.textView2
        val img = binding.imageView4
        val context = context

        init{
            item.setOnClickListener{
                listener.onItemClick(adapterPosition, )
            }
        }

        fun bind(snews:sNews){
            text.text = snews.title

            val url = snews.urlToImage
            Glide.with(img)
                .load(url)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .fallback(R.drawable.image)
                .into(img)
        }


        val db = sDb.getNewsDb(context)
        fun deleteSavedItem(snews:sNews){
            binding.deleteBtn.setOnClickListener {
                builder.setTitle("Delete News from saves")
                    .setMessage("Are you sure delete this news? ")
                    .setPositiveButton("Yes"){inter, it ->
                        Thread{
                            db.getNewsDao().deleteNewsByUrl(snews.urlToImage)
                        }.start()
                        val int = Intent(context, SavedNews::class.java)
                        context.startActivity(int)
                    }
                    .setNegativeButton("No"){inter, it ->
                        inter.cancel()
                    }
                    .show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): sHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.saved_news_item, parent, false)
        return sHolder(view, mListener, context)
    }

    override fun getItemCount(): Int {
        return snewsList.size
    }

    override fun onBindViewHolder(holder: sHolder, position: Int) {
        holder.bind(snewsList[position])
        holder.deleteSavedItem(snewsList[position])
    }


}