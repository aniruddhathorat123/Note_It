package com.aniruddha.writerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.file_list_item.view.*

/**
 * Adapter class used to shows the list of all notes which created by users.
 */
class FileListAdapter(
    private var items : MutableList<String>,
    private val listener: FilesListFragment):
    RecyclerView.Adapter<FileListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.file_list_item,
            parent,
            false
            )
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        holder.bind(items[position])
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(data: String){
            itemView.fileItem.text = data
            itemView.setOnClickListener{
                listener.onFileNameClick(adapterPosition)
            }
            itemView.setOnLongClickListener {
                listener.onLongFileNameClick((items[adapterPosition]),it)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnFileNameClickListener{
        fun onFileNameClick(position : Int)
        fun onLongFileNameClick(item: String,view: View)
    }
}
