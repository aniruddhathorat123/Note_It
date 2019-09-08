package com.aniruddha.writerapp

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_file_data_container.*
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_files_list.*

/**
 * A simple [Fragment] subclass.
 */
class FileDataContainerFragment : Fragment(), OnBackPressed {

    companion object{
        fun getInstance(file: String) =
            FileDataContainerFragment().apply {
                fileName = file
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(!menu.hasVisibleItems()) {
            inflater.inflate(R.menu.file_details_fragment_menu, menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private lateinit var fileName: String
    private lateinit var availableFileData: String
    private val file = FileHandler.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(savedInstanceState != null)
            fileName = savedInstanceState.getString(WriterConstants.FILE_NAME)!!
        val view = inflater.inflate(R.layout.fragment_file_data_container,
            container,
            false)
        //To enable the back button on action bar
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.subtitle = fileName
        setHasOptionsMenu(true)
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.saveFileMenu -> {
                file.saveFileData(fileName,fileData.text.toString())
                return true
            }
            android.R.id.home -> {
                saveEditData()
                requireActivity().supportFragmentManager.popBackStack()
                return true
            }
        }
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        availableFileData = file.getFileData(fileName)
        fileData.setText(availableFileData)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(WriterConstants.FILE_NAME,fileName)
        super.onSaveInstanceState(outState)
    }

    /**
     * Called when user press the back button.
     * check whether user modify file content or not.
     */
    override fun saveEditData() {
        val currentFileData = fileData.text.toString()
        if(availableFileData != currentFileData) {
            DialogBuilders(requireContext()).fileSaveDialogBuilder(
                fileName,
                fileData.text.toString()
            ).show()
        }
    }
}

/**
 * Handle the back press on FileDataContainerFragment
 */
interface OnBackPressed{
    fun saveEditData()
}