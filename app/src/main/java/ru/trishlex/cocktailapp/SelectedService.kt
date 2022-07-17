package ru.trishlex.cocktailapp

import android.content.SharedPreferences
import org.openapitools.client.JsonUtil

abstract class SelectedService<T: Item>(
    private val selectedItems: HashMap<T, Int>,
    private val sharedPreferences: SharedPreferences,
    private val key: String
) {

    fun isSelected(item: T): Boolean {
        return selectedItems.containsKey(item)
    }

    fun addItem(item: T) {
        if (selectedItems.containsKey(item)) {
            return
        }
        selectedItems[item] = selectedItems.size
        val editor = sharedPreferences.edit()
        val selected = getSelectedItems().associate { it.id to it.name }
        editor.putString(key, JsonUtil.getGson().toJson(selected))
        editor.apply()
    }

    fun removeItem(item: T) {
        if (!selectedItems.containsKey(item)) {
            return
        }
        selectedItems.remove(item)
        var currentIndex = 0
        for (i in selectedItems.keys) {
            selectedItems[i] = currentIndex++
        }

        val editor = sharedPreferences.edit()
        val selected = getSelectedItems().associate { it.id to it.name }
        editor.putString(key, JsonUtil.getGson().toJson(selected))
        editor.apply()
    }

    fun getSelectedItemIds(): ArrayList<Int> {
        return ArrayList(selectedItems.keys.map { it.id })
    }

    private fun getSelectedItems(): List<T> {
        return selectedItems.keys.toList()
    }
}