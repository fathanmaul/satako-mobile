package dev.capstone.satako_mobile.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.viewbinding.ViewBinding
import java.io.File

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun File.delete(context: Context): Boolean {
    var selectionArgs = arrayOf(this.absolutePath)
    val contentResolver = context.getContentResolver()
    var where: String? = null
    var filesUri: Uri? = null
    if (android.os.Build.VERSION.SDK_INT >= 29) {
        filesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        where = MediaStore.Images.Media._ID + "=?"
        selectionArgs = arrayOf(this.name)
    } else {
        where = MediaStore.MediaColumns.DATA + "=?"
        filesUri = MediaStore.Files.getContentUri("external")
    }

    val int = contentResolver.delete(filesUri!!, where, selectionArgs)
    Toast.makeText(context, "Deleted: $int", Toast.LENGTH_SHORT).show()

    return !this.exists()
}

fun String.removeWhiteSpace(): String {
    return this.replace("\\s+".toRegex(), "")
}

fun String.isEmailValid(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isNameValid(): Boolean {
    return this.length in 3..50
}