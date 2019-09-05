package com.aniruddha.writerapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_file_data_container.*
import android.view.MenuInflater

/**
 * A simple [Fragment] subclass.
 */
class FileDataContainerFragment : Fragment() {

    companion object{
        fun getInstance(file: String) =
            FileDataContainerFragment().apply {
                fileName = file
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(!menu.hasVisibleItems())
            inflater.inflate(R.menu.file_details_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private lateinit var fileName: String
    private val file = FileHandler.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState != null)
            fileName = savedInstanceState.getString(WriterConstants.FILE_NAME)!!
        val view = inflater.inflate(R.layout.fragment_file_data_container, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fileData.setText(file.getFileData(fileName))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(WriterConstants.FILE_NAME,fileName)
        super.onSaveInstanceState(outState)
    }
}
