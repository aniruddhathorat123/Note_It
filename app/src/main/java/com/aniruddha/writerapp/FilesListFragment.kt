package com.aniruddha.writerapp

import android.app.AlertDialog
import android.graphics.ColorSpace
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_files_list.*

class FilesListFragment : Fragment(),
    FileListAdapter.OnFileNameClickListener,
    DialogBuilders.DialogFunctionCall {
    companion object {
        fun getInstance() = FilesListFragment()
    }

    //store the list of file, if no files then it empty.
    private lateinit var data: MutableList<String>
    private lateinit var adapter : FileListAdapter
    private lateinit var dbHandler: DatabaseHandler
    private val fileHandler = FileHandler.getInstance()

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
            //Toast.makeText(requireContext(),"Dir:"+requireContext().filesDir,Toast.LENGTH_LONG).show()
            val alertDialog: AlertDialog = DialogBuilders(requireContext(),this)
                .fileCreationDialogBuilder()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        dbHandler = DatabaseHandler.getInstance(requireContext())
        data = dbHandler.getFileList()
        if (data.isNotEmpty()){
            //fileListFragment.visibility = View.VISIBLE
            adapter = FileListAdapter(data,this)
            fileListRecyclerView.apply {
                this.adapter = this@FilesListFragment.adapter
                this.layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false)
            }
        }
        else{
            noFilesTextView.visibility = View.VISIBLE
        }
        mainProgressBar.visibility = View.GONE
    }

    //create dialog for file creation

    override fun createNewFile(name:String) {
        Toast.makeText(requireContext(),"File name: $name",Toast.LENGTH_LONG).show()
        for(i in 0.rangeTo(data.size-1)) {
            if(data[i]==name) {
                Toast.makeText(requireContext(),
                    "File with name:'$name' already exists",
                    Toast.LENGTH_LONG).show()
                return
            }
        }
        dbHandler.insertFileList(name)
        fileHandler.createNewFile(name)
        validateAdapter(0)
    }

    /**
     * Recreate the object of FileListAdapter as new file is added and
     * reflected to the database, but not in local file list parameter:
     * [data] and not in parameter of FileListAdapter.
     *
     * it also accessed from DialogBuilder in rename file case
     */
    fun validateAdapter(id: Int){
        when(id){
            0 -> {
                data = dbHandler.getFileList()
                adapter = FileListAdapter(data,this)
                fileListRecyclerView.adapter = adapter
            }
            else -> {
                data = dbHandler.getFileList()
                adapter = FileListAdapter(data,this)
                fileListRecyclerView.invalidate()
                fileListRecyclerView.adapter = adapter
            }
        }
    }

    override fun onFileNameClick(position : Int) {
        val fragment = FileDataContainerFragment.getInstance(data[position])
        (requireActivity() as MainActivity).replaceFragment(fragment)

    }

    override fun onLongFileNameClick(item: String,view: View) {
        Toast.makeText(requireContext(),"Long click on :$item", Toast.LENGTH_LONG).show()
        val popupMenu= PopupMenu(requireContext(),view)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.renameFileMenu -> {
                    Toast.makeText(requireContext(),"Rename file clicked",Toast.LENGTH_LONG)
                        .show()
                    renameFileClicked(item)
                    true
                }
                else -> {
                    Toast.makeText(requireContext(),"Delete file clicked",Toast.LENGTH_LONG)
                        .show()
                    deleteFile(item)
                    validateAdapter(1)
                    true
                }
            }
        }
        popupMenu.inflate(R.menu.menu_main)
        popupMenu.show()
    }

    private fun renameFileClicked(oldName:String) {
        val alertDialog: AlertDialog = DialogBuilders(requireContext(),this)
            .fileRenameDialogBuilder(oldName)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteFile(name: String) {
        val alertDialog: AlertDialog = DialogBuilders(requireContext(), this)
            .fileDeleteDialogBuilder(name)
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
