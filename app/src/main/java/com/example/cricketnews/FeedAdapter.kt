package com.example.cricketnews

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class ViewHolder(v:View){
    val tvTitle:TextView=v.findViewById(R.id.tvTitle)
    val tvPubDate:TextView=v.findViewById(R.id.tvPubDate)
    val tvDescription:TextView=v.findViewById(R.id.tvDescription)
    val tvCoverImage:TextView=v.findViewById(R.id.tvCoverImage)
    val tvLink:TextView=v.findViewById(R.id.tvLink)
    val imageView:ImageView= v.findViewById(R.id.imageView)
}

class FeedAdapter (context: Context,private val resource :Int, private val applications: List<FeedEntry>)
    : ArrayAdapter<FeedEntry>(context,resource){

    private val inflater=LayoutInflater.from(context)
    override fun getCount(): Int {
        return applications.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View
        val viewHolder:ViewHolder

        if(convertView == null){
            view = inflater.inflate(resource,parent,false)
            viewHolder= ViewHolder(view)
            view.tag=viewHolder
        }else{
            view=convertView
            viewHolder=view.tag as ViewHolder
        }
        val currentApp=applications[position]
        viewHolder.tvTitle.text=currentApp.title
        viewHolder.tvPubDate.text=currentApp.pubDate
        viewHolder.tvDescription.text=currentApp.description
        viewHolder.tvCoverImage.text=currentApp.coverImage
        viewHolder.tvLink.text=currentApp.link

        viewHolder.tvLink.setOnClickListener{
            val queryUrl:Uri= Uri.parse(viewHolder.tvLink.text.toString())
            val intent=Intent(Intent.ACTION_VIEW,queryUrl)
            context.startActivity(intent)
        }

        Glide.with(context).load(viewHolder.tvCoverImage.text.toString()).into(viewHolder.imageView)
        return view
    }
}