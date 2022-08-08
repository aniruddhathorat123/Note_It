package com.aniruddha.writerapp;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class used to export or save note to the specified storage location of device.
 * Note: it only export or save the note, it does not move that note.
 */
public class ExportOutFile {
    private static final String EXPORT_DIR = "/Note it";

    private static final FileHandler fileHandler = FileHandler.Companion.getInstance();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void shareSingleFile(Context context, String filename) {
        Toast.makeText(context, "Sharing file", Toast.LENGTH_SHORT).show();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                /*File file = new File(context.getExternalFilesDir(".txt").toString() + "/" + filename + ".txt");
                fileHandler.copyFile(filename, file);*/

                /*Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + fileHandler.getFile(filename).getPath()));
                context.startActivity(Intent.createChooser(sharingIntent, "share file with"));*/
            }
        });
    }

    public static void exportSingleFile(Context context, String filename) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    insertIntoMediaStore(context, filename);
                } else {
                    insertIntoLegacyStorage(context, filename);
                }
            }
        });
        Toast.makeText(context, "Saving file", Toast.LENGTH_SHORT).show();
    }

    private static void saveDataIntoFile(Context context, Uri sourceFile, Uri destFile)
            throws FileNotFoundException {
        ContentResolver resolver = context.getContentResolver();
        OutputStream outputStream = resolver.openOutputStream(destFile, "rw");
        InputStream inputStream = resolver.openInputStream(sourceFile);
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private static void insertIntoMediaStore(Context context, String filename) {
        ContentResolver resolver = context.getContentResolver();
        Uri fileCollection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);

        ContentValues fileValues = new ContentValues();
        fileValues.put(MediaStore.Files.FileColumns.DISPLAY_NAME,
                filename + "_" + System.currentTimeMillis() + ".txt");
        fileValues.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");
        fileValues.put(MediaStore.Files.FileColumns.IS_PENDING, 1);

        Uri fileUri = resolver.insert(fileCollection, fileValues);
        if (fileUri == null) {
            Toast.makeText(context, "Unable to save file", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            OutputStream outputStream = resolver.openOutputStream(fileUri, "rw");
            InputStream inputStream =
                    resolver.openInputStream(Uri.fromFile(fileHandler.getFile(filename)));
            FileUtils.copy(inputStream, outputStream);

            inputStream.close();
            outputStream.close();

            fileValues.clear();
            fileValues.put(MediaStore.Files.FileColumns.IS_PENDING, 0);
            resolver.update(fileUri, fileValues, null, null);
        } catch (IOException e) {
            e.printStackTrace();
            //Toast.makeText(context, "Unable to save file", Toast.LENGTH_SHORT).show();
            File file = new File(fileUri.getPath());
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private static void insertIntoLegacyStorage(Context context, String filename) {
        File file =
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        EXPORT_DIR);
        try {
            if (!file.createNewFile()) {
                return;
            }
            ContentResolver resolver = context.getContentResolver();
            OutputStream outputStream = resolver.openOutputStream(Uri.fromFile(file));
            InputStream inputStream =
                    resolver.openInputStream(Uri.fromFile(fileHandler.getFile(filename)));

            byte[] buf = new byte[8192];
            int length;
            while ((length = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }

            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            //Toast.makeText(context, "Unable to save file", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /*private void createFile(Uri pickerInitialUri) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        intent.putExtra(Intent.EXTRA_TITLE, "invoice.pdf");

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when your app creates the document.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(intent, CREATE_FILE);
    }*/
}
