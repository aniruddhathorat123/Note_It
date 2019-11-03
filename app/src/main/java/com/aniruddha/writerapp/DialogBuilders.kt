package com.aniruddha.writerapp

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.Toast

class DialogBuilders(private val context: Context){

    constructor(context: Context, listener: FilesListFragment):
            this(context) {
        this.listener = listener
    }

    private lateinit var newFileName: EditText
    private val dbhandler = DatabaseHandler.getInstance(context)
    private val fileHandler = FileHandler.getInstance()
    private lateinit var listener: FilesListFragment

    fun fileCreationDialogBuilder(): AlertDialog{
        newFileName = EditText(context)
        newFileName.inputType = InputType.TYPE_CLASS_TEXT
        newFileName.hint = "File Name"
        val builder = AlertDialog.Builder(context)
        builder.setTitle("New File")
        builder.setMessage("Enter the name of new file:")
        builder.setView(newFileName)

        builder.setPositiveButton(android.R.string.yes){dialog, which ->
            listener.createNewFile(newFileName.text.toString())
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
        }
        return builder.create()
    }

    fun fileRenameDialogBuilder(oldName: String): AlertDialog{
        newFileName = EditText(context)
        newFileName.inputType = InputType.TYPE_CLASS_TEXT
        newFileName.setText(oldName)

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Rename File")
        builder.setMessage("Enter the new name of file:")
        builder.setView(newFileName)

        builder.setPositiveButton("Rename"){dialog, which ->
            dbhandler.updateFileName(oldName,newFileName.text.toString())
            fileHandler
                .renameFile(oldName,newFileName.text.toString())
            listener.validateAdapter(1)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
        }
        return builder.create()
    }

    fun fileDeleteDialogBuilder(file: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle("Delete File")
            .setMessage("Are you sure to delete file : '$file'?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                dbhandler.deleteFile(file)
                fileHandler.deleteFile(file)
                listener.validateAdapter(1)
                Toast.makeText(context,"'$file' deleted successfully",Toast.LENGTH_LONG)
                    .show()
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
            }
        return builder.create()
    }

    fun fileSaveDialogBuilder(file: String, data: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle("Save File '$file'")
            .setMessage("Want to save data before leave?")
            .setPositiveButton(android.R.string.yes) { dialog, which ->
                fileHandler.saveFileData(file,data)
            }
            .setNegativeButton(android.R.string.no) { dialog, which ->
            }
        return builder.create()
    }

    interface DialogFunctionCall{
        fun createNewFile(name: String)
    }
}