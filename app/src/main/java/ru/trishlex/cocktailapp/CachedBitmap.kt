package ru.trishlex.cocktailapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class CachedBitmap(): Serializable {

    private val serialVersionUID = -6298516694275121291L

    @Transient
    lateinit var bitmap: Bitmap

    constructor(bitmap: Bitmap): this() {
        this.bitmap = bitmap
    }

    @Throws(IOException::class)
    private fun writeObject(oos: ObjectOutputStream) {
        // This will serialize all fields that you did not mark with 'transient'
        // (Java's default behaviour)
        oos.defaultWriteObject()
        // Now, manually serialize all transient fields that you want to be serialized
        val byteStream = ByteArrayOutputStream()
        val success = bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteStream)
        if (success) {
            oos.writeObject(byteStream.toByteArray())
        }
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(ois: ObjectInputStream) {
        // Now, all again, deserializing - in the SAME ORDER!
        // All non-transient fields
        ois.defaultReadObject()
        // All other fields that you serialized
        val image = ois.readObject() as ByteArray
        if (image != null && image.isNotEmpty()) {
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        }
    }
}