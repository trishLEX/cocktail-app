package ru.trishlex.cocktailapp

abstract class PickerService<T: PickedItem> {

    private val pickedItems = LinkedHashSet<T>()

    fun add(item: T) {
        pickedItems.remove(item)
        pickedItems.add(item)
    }

    fun remove(item: T) {
        pickedItems.remove(item)
    }

    fun contains(item: T): Boolean {
        return pickedItems.contains(item)
    }

    fun getPicked(): Set<T> {
        return pickedItems
    }

    fun removeAll() {
        pickedItems.clear()
    }
}