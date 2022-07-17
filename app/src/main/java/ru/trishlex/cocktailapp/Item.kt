package ru.trishlex.cocktailapp

import android.graphics.Bitmap

abstract class Item(
    val id: Int,
    val name: String,
    val preview: Bitmap?,
    var isSelected: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Item) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}