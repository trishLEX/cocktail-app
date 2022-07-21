package ru.trishlex.cocktailapp

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class LocalStorageUtil {

    companion object {

        fun getLocalBitmapUri(bitmap: Bitmap, name: String, context: Context): Uri {
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            fos.close()
            fos.flush()
            return FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
        }
    }
}