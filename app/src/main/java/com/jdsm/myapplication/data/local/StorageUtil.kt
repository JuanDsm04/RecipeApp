package com.jdsm.myapplication.data.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun saveImageToInternalStorage(
    context: Context,
    imageUri: Uri

): String {
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream( imageUri)
    val file = File(context.filesDir, "recipe_images" )

    if (!file.exists()) {
        file.mkdirs()
    }

    val fileName = "${System.currentTimeMillis()}.jpg"
    val outputFile = File(file, fileName)
    val outputStream = FileOutputStream(outputFile)

    inputStream?.copyTo(outputStream)
    inputStream?.close()
    outputStream.close()

    return outputFile.absolutePath
}
