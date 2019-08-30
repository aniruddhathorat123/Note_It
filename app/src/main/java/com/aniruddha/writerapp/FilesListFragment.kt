package com.aniruddha.writerapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_files_list.*
import java.io.File
import java.util.ArrayList

class FilesListFragment : Fragment(),FileListAdapter.OnFileNameClickListener {

    companion object {
        fun getInstance() = FilesListFragment()
    }

    private lateinit var data: ArrayList<String>
    private lateinit var adapter : FileListAdapter
    private lateinit var filehandler: FileHandler
    private lateinit val dialog = AlertDialog.Builder(this.dialogBuilder())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_files_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFileButton.setOnClickListener{

        }
        val file = File(WriterConstants.WRITER_FILE)
        if (file.exists()){
            filehandler = FileHandler.getInstance()
            data = filehandler.getStringData(file)
            fileListFragment.visibility = View.VISIBLE
            adapter = FileListAdapter(data,this)
            fileListRecyclerView.apply {
                this.adapter = this@FilesListFragment.adapter
            }
        }
        else{
            mainProgressBar.visibility = View.GONE
            noFilesTextView.visibility = View.VISIBLE
        }
    }

    fun dialogBuilder(){
        with(dialog){

        }
    }

    override fun onFileNameClick(position : Int){
        Toast.makeText(requireContext(),"click on :$position", Toast.LENGTH_LONG).show()
    }
}