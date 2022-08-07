package ru.trishlex.cocktailapp

import android.graphics.Bitmap
import java.io.Serializable

abstract class PickedItem(
    val id: Int,
    val name: String,
    @Transient
    val preview: Bitmap,
    var isPicked: Boolean = false
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PickedItem) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}