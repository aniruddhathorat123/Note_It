package com.aniruddha.writerapp


import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_files_list.*
import java.util.ArrayList

class FilesListFragment : Fragment(),FileListAdapter.OnFileNameClickListener {
    companion object {
        fun getInstance(list: ArrayList<String>) =
            FilesListFragment().apply {
            this.list = list
        }
    }

    private lateinit var list: ArrayList<String>
    private val adapter = FileListAdapter(list,requireContext())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_files_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fileListRecyclerView.apply {
            this.adapter = this@FilesListFragment.adapter
        }
    }

    override fun onFileNameClick(position : Int){
    }
}



