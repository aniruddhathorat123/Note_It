package com.aniruddha.writerapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_file_data_container.*
import android.view.MenuInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.checkSelfPermission
import android.app.Activity
import java.io.IOException
import java.lang.IllegalArgumentException


/**
 * Fragment shows the contents of file and user can edit and save the contents.
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
            /* Save updated file contents.*/
            R.id.saveFileMenu -> {
                availableFileData = fileData.text.toString()
                file.saveFileData(fileName, availableFileData)
                return true
            }
            R.id.shareFileMenu -> {
                if (availableFileData.isEmpty()) {
                    Toast.makeText(requireContext(), "Can't share empty file", Toast.LENGTH_SHORT)
                        .show()
                }
                ExportOutFile.shareSingleFile(requireContext(), fileName)
                /*Toast.makeText(requireContext(),
                    R.string.service_not_available_text,
                    Toast.LENGTH_LONG).show()*/
            }
            /* Export current file to defined location. */
            R.id.exportFileMenu -> {
                if (availableFileData.isEmpty()) {
                    Toast.makeText(requireContext(), "Can't export empty file", Toast.LENGTH_SHORT)
                        .show()
                    return true;
                }
                if (checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Storage permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                    return true;
                }
                createFile()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        /**
         * Export the file to specified location by user.
         */
        if (requestCode == WriterConstants.CREATE_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that the user selected.
            var uri: Uri? = null
            if (data != null) {
                try {
                    uri = data.data
                    if (uri == null || uri.path.isNullOrEmpty()) {
                        throw IllegalArgumentException("Unable to save on Empty file Uri.")
                    }
                    val resolver = requireContext().contentResolver
                    val outputStream =
                        resolver.openOutputStream(uri, "rw")
                            ?: throw IOException("Error in OutputStream")

                    val inputStream =
                        resolver.openInputStream(Uri.fromFile(file.getFile(fileName)))
                            ?: throw IOException("Error in InputStream")
                    val buf = ByteArray(8192)
                    var length: Int
                    while (inputStream.read(buf).also { length = it } > 0) {
                        outputStream.write(buf, 0, length)
                    }
                    outputStream.close()
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Unable to save file", Toast.LENGTH_SHORT)
                        .show()
                } catch (e: IllegalArgumentException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Unable to save file", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
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
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.subtitle = null
    }

    /**
     * This function navigate the user to storage location where user want to store the file.
     */
    private fun createFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "$fileName.txt")

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
        //intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        startActivityForResult(intent, WriterConstants.CREATE_FILE_REQUEST_CODE)
    }
}

/**
 * Handle the back press on FileDataContainerFragment
 */
interface OnBackPressed{
    fun saveEditData()
}