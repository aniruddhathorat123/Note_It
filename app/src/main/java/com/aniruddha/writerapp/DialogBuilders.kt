package com.aniruddha.writerapp

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.Toast

class DialogBuilders(private val context: Context, private val listner: FilesListFragment){
    private lateinit var newFileName: EditText
    private val dbhandler = DatabaseHandler.getInstance(context)
    private val fileHandler = FileHandler.getInstance()

    fun fileCreationDialogBuilder(): AlertDialog{
        newFileName = EditText(context)
        newFileName.inputType = InputType.TYPE_CLASS_TEXT
        newFileName.hint = "File Name"
        val builder = AlertDialog.Builder(context)
        builder.setTitle("New File")
        builder.setMessage("Enter the name of new file:")
        builder.setView(newFileName)

        builder.setPositiveButton("Yes"){dialog, which ->
            listner.createNewFile(newFileName.text.toString())
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

        builder.setPositiveButton("Yes"){dialog, which ->
            dbhandler.updateFileName(oldName,newFileName.text.toString())
            fileHandler
                .renameFile(oldName,newFileName.text.toString())
            listner.validateAdapter(1)
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
        }
        return builder.create()
    }

    fun fileDeleteDialogBuilder(file: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle("Delete File")
            .setMessage("Are you sure to delete file : '$file'?")
            .setPositiveButton("Yes") { dialog, which ->
                dbhandler.deleteFile(file)
                fileHandler.deleteFile(file)
                listner.validateAdapter(1)
            }
            .setNegativeButton("No") { dialog, which ->
            }
        return builder.create()
    }

    interface DialogFunctionCall{
        fun createNewFile(name: String)
    }

}