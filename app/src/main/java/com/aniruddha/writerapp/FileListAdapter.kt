package com.aniruddha.writerapp
import android.content.Context
import android.system.Os.bind
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.file_list_item.view.*
import java.io.File

class FileListAdapter(private val items : ArrayList<String>,val context: Context):
    RecyclerView.Adapter<FileListAdapter.ViewHolder>(){

    private lateinit var listener : OnFileNameClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.file_list_item,
            parent,
            false
            )
        return ViewHolder(view)
    }

    fun setClickListener(listener: OnFileNameClickListener){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.bind(position,items[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(position: Int,data: String){
            itemView.fileItem.text = data
            
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
    interface OnFileNameClickListener{
        fun onFileNameClick(position : Int)
    }

}
