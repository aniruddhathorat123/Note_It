package com.aniruddha.writerapp
import android.view.View
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileListAdapter(val items : ArrayList<String>,val context: Context):
    RecyclerView.Adapter<FileListAdapter.MyAdapter>(){

    class MyAdapter(val filename : TextView):RecyclerView.ViewHolder(filename)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        return MyAdapter(LayoutInflater.from(context)
            .inflate(R.layout.file_list_item,parent,false) as TextView)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        holder.filename.text = items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

interface ItemClickListener{
    fun setOnClickListener(position : Int)
}