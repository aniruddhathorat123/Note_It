package com.aniruddha.writerapp

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import android.widget.Toast

/**
 * Class handles all the operation related to creating dialog.
 */
class DialogBuilders(private val context: Context){

    constructor(context: Context, listener: FilesListFragment):
            this(context) {
        this.listener = listener
    }

    private lateinit var newFileName: EditText
    private val dbHandler = DatabaseHandler.getInstance(context)
    private val fileHandler = FileHandler.getInstance()
    private lateinit var listener: FilesListFragment

    @Deprecated("File name get directly from the Utils#getFileName")
    fun fileCreationDialogBuilder(): AlertDialog{
        newFileName = EditText(context)
        newFileName.inputType = InputType.TYPE_CLASS_TEXT
        newFileName.hint = context.getString(R.string.new_note_hint_text)
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.new_note_title_text)
        builder.setMessage(R.string.new_note_message_text)
        builder.setView(newFileName)

        builder.setPositiveButton(R.string.create_note_button_text){ dialog, which ->
            listener.createNewFile(newFileName.text.toString())
        }
        builder.setNegativeButton(R.string.cancel_dialog_button_text) { dialog, which ->
        }
        return builder.create()
    }

    fun fileRenameDialogBuilder(oldName: String): AlertDialog{
        newFileName = EditText(context)
        newFileName.inputType = InputType.TYPE_CLASS_TEXT
        newFileName.setText(oldName)

        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.rename_note_title_text)
        builder.setMessage(R.string.new_note_message_text)
        builder.setView(newFileName)
        builder.setPositiveButton(R.string.rename_note_button_text){dialog, which ->
            dbHandler.updateFileName(oldName,newFileName.text.toString())
            fileHandler
                .renameFile(oldName,newFileName.text.toString())
            listener.validateAdapter(1)
        }
        builder.setNegativeButton(R.string.cancel_dialog_button_text) { dialog, which ->
        }
        return builder.create()
    }

    fun fileDeleteDialogBuilder(file: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle(R.string.delete_note_title_text)
            .setMessage(String.format(context.getString(R.string.delete_note_message_text), file))
            //.setMessage("Are you sure to delete file : '$file'?")
            .setPositiveButton(R.string.delete_note_button_text) { dialog, which ->
                dbHandler.deleteFile(file)
                fileHandler.deleteFile(file)
                listener.validateAdapter(1)
                Toast.makeText(context,
                    String.format(context.getString(R.string.delete_success_toast_text), file),
                    Toast.LENGTH_LONG)
                    .show()
            }
            .setNegativeButton(R.string.cancel_dialog_button_text) { dialog, which ->
            }
        return builder.create()
    }

    fun fileSaveDialogBuilder(file: String, data: String): AlertDialog {
        val builder = AlertDialog.Builder(context)
            .setTitle(String.format(context.getString(R.string.save_note_title_text), file))
            .setMessage(R.string.save_note_message_text)
            .setPositiveButton(R.string.save_note_button_text) { dialog, which ->
                fileHandler.saveFileData(file,data)
            }
            .setNegativeButton(R.string.cancel_dialog_button_text) { dialog, which ->
            }
        return builder.create()
    }

    interface DialogFunctionCall{
        fun createNewFile(name: String)
    }
}