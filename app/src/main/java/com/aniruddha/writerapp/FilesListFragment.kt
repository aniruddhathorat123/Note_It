package com.aniruddha.writerapp

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_files_list.*

/**
 *
 */
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
            val alertDialog: AlertDialog = DialogBuilders(requireContext(),this)
                .fileCreationDialogBuilder()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        dbHandler = DatabaseHandler.getInstance(requireContext())
        data = dbHandler.getFileList()
        adapter = FileListAdapter(data,this)
        fileListRecyclerView.apply {
            this.adapter = this@FilesListFragment.adapter
            this.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,
                false)
        }
        if (data.isEmpty()){
            //fileListFragment.visibility = View.VISIBLE
            noFilesTextView.visibility = View.VISIBLE
        }
        mainProgressBar.visibility = View.GONE
    }

    //create dialog for file creation
    override fun createNewFile(name: String) {
        for(i in 0.rangeTo(data.size-1)) {
            if(data[i] == name) {
                Toast.makeText(requireContext(),
                    "File with name:'$name' already exists",
                    Toast.LENGTH_LONG).show()
                return
            }

            if (name.isEmpty()) {
                Toast.makeText((requireActivity()),
                    "Invalid File name",
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
                updateNoFileState(data.size)
            }
            else -> {
                data = dbHandler.getFileList()
                adapter = FileListAdapter(data,this)
                fileListRecyclerView.invalidate()
                fileListRecyclerView.adapter = adapter
                updateNoFileState(data.size)
            }
        }
    }

    override fun onFileNameClick(position : Int) {
        val fragment = FileDataContainerFragment.getInstance(data[position])
        (requireActivity() as MainActivity).replaceFragment(fragment)
    }

    override fun onLongFileNameClick(item: String,view: View) {
        val popupMenu= PopupMenu(requireContext(),view)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId){
                R.id.renameFileMenu -> {
                    renameFileClicked(item)
                    true
                }
                else -> {
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

    private fun updateNoFileState(size: Int) {
        if (size > 0) {
            noFilesTextView.visibility = View.GONE
        } else {
            noFilesTextView.visibility = View.VISIBLE
        }
    }
}
