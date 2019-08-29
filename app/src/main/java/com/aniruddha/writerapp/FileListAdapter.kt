package com.aniruddha.writerapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.file_list_item.view.*

class FileListAdapter(private val items : ArrayList<String>,context: Context):
    RecyclerView.Adapter<FileListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.file_list_item,
            parent,
            false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(items[position])
    }

    class ViewHolder(view: View):
        RecyclerView.ViewHolder(view){

        fun bind(data: String){
            itemView.fileItem.text = data
            itemView.setOnClickListener {
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    interface OnFileNameClickListener{
        fun onFileNameClick(position : Int)
    }

}
