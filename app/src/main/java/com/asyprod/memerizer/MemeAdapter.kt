package com.asyprod.memerizer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MemeAdapter(private val ctx: Context): RecyclerView.Adapter<MemeViewHolder>() {
    private val memeArray:ArrayList<Meme> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meme_item,parent,false)
        return MemeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemeViewHolder, position: Int) {
        holder.usernameTop.text = memeArray[position].userName
        holder.usernameBottom.text = memeArray[position].userName
        holder.title.text = memeArray[position].title
        holder.upvotes.text = "${memeArray[position].upvotes} Upvotes"
        Glide.with(ctx).load(memeArray[position].url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).placeholder(R.drawable.image_placeholder).into(holder.imageMeme)
        holder.shareImg.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_TEXT,"Hey checkout this funny Meme I found from MemeRizer App!\n${memeArray[position].url}")
            val chooser = Intent.createChooser(i,"Share this meme using...")
            startActivity(ctx,chooser,null)
        }
    }

    override fun getItemCount(): Int {
        return memeArray.size
    }

    fun refresh(updated: ArrayList<Meme>){
        memeArray.clear()
        memeArray.addAll(updated)
        notifyDataSetChanged()
    }

}

class MemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val usernameTop = itemView.findViewById<TextView>(R.id.usernameTop)
    val usernameBottom = itemView.findViewById<TextView>(R.id.usernameBottom)
    val imageMeme = itemView.findViewById<ImageView>(R.id.memeImage)
    val title = itemView.findViewById<TextView>(R.id.title)
    val shareImg = itemView.findViewById<ImageView>(R.id.share)
    val upvotes = itemView.findViewById<TextView>(R.id.upvotes)
}
