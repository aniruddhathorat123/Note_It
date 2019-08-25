package com.aniruddha.writerapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

class FilesListFragment : Fragment() {
    companion object{
        lateinit var list : ArrayList<String>

        fun getInstance(bundle: Bundle):FilesListFragment{
            list = bundle.getStringArrayList(WriterConstants.FILES_LIST)
            return FilesListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files_list, container, false)
    }
}



